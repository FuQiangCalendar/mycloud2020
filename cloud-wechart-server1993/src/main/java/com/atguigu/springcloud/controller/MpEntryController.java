package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信公众号/服务号入口
 *
 * @author huan.fu 2020/12/19 - 下午3:30
 */
@Component
@RestController
@Slf4j
public class MpEntryController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    /**
     * 微信接入
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echoStr   随机字符串
     * @return 接入成功返回 echoStr 的值，否则随便返回
     */
    @GetMapping("/wx")
    public String entry(@RequestParam("signature") String signature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce,
                        @RequestParam("echostr") String echoStr) {
        log.info("微信公众号/服务号接入传递的参数 signature:[{}],timestamp:[{}],nonce:[{}],echostr:[{}]",
                signature, timestamp, nonce, echoStr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echoStr)) {
            log.error("接收到微信认证信息,参数非法,存在为空的参数");
            return "error";
        }

        boolean result = wxMpService.checkSignature(timestamp, nonce, signature);
        log.info("微信公众号/服务号接入成功?[{}]", result);
        return result ? echoStr : "error";
    }

    /**
     * 微信回调
     *
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping("/wx")
    public String entryCallback(@RequestBody String requestBody,
                                @RequestParam("signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("openid") String openid,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature,
                                HttpServletRequest req, HttpServletResponse resp) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        WxMpXmlOutMessage outMessage = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            outMessage = this.wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            log.info("\n消息解密后内容为：\n[{}] ", inMessage.toString());
            outMessage = this.wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }
        log.info("\n组装回复信息：[{}]", out);
        return out;
    }
}