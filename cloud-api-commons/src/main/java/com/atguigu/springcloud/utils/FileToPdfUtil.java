package com.atguigu.springcloud.utils;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import com.aspose.words.Document;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
 
/**
 * @ClassName：FileToPdfUtils
 * @Description: 文件转pdf工具类  linux 服务器需要安装字体不然是乱码
 * @Author: liuhm
 * @Date: 2019/10/10 13:38
 */
public class FileToPdfUtil {
    /**
     * @description: 文件转pdf
     * @params: source:源文件地址 如：C://test/test.doc,target:转换后文件路径  如 C://test/pdf
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 15:17
     */
    public static String officeToPdf(String source, String target) {
        File file=new File(source);
        // 文件名字
        String fileName=file.getName();
        //office文档转pdf
        String fileExt = source.substring(source.lastIndexOf(".")+1);
        if ("doc".equals(fileExt) || "docx".equals(fileExt)) {
            doc2pdf(source, target,fileExt);
        }
        if ("xls".equals(fileExt) || "xlsx".equals(fileExt)) {
            excel2pdf(source, target,fileExt);
        }
        if ("ppt".equals(fileExt) || "pptx".equals(fileExt)) {
            ppt2pdf(source, target,fileExt);
        }
 
        if("doc,docx,xls,xlsx,ppt,pptx".indexOf(fileExt)>0){
            return target+"/"+(fileName.replace(fileExt,"pdf"));
        }
        return null;
    }
 
    /**
     * @description: 验证ExcelLicense
     * @params:
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 13:40
     */
    public static boolean getExcelLicense() {
        boolean result = false;
        try {
            //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            InputStream is = FileToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.cells.License aposeLic = new  com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @description: 验证PPTtlLicense
     * @params:
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 13:40
     */
    public static boolean getPPTLicense() {
        boolean result = false;
        try {
            //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            InputStream is = FileToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @description: 验证License
     * @params:
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 13:40
     */
    public static boolean getDocLicense() {
        boolean result = false;
        try {
            //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            InputStream is = FileToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @description:  excel转pdf
     * @params:  source:源文件地址,target:转换后文件路径,fileExt:后缀
     * @return: 
     * @author: liuhm
     * @Date: 2019/10/10 13:41
     */
    public static void excel2pdf(String source, String target,String fileExt) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getExcelLicense()) {
            return;
        }
        try {
            // 原始excel路径
            Workbook wb = new Workbook(source);
            //验证路径
            try {
                if (!(new File(target).isDirectory())) {
                    new File(target).mkdirs();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            // 文件名字
            String fileName=source.substring(source.lastIndexOf("/")+1);
            // 输出路径
            File pdfFile = new File(target+"/"+(fileName.replace(fileExt,"pdf")));
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS,com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * @description: ppt转pdf
     * @params: source:源文件地址,target:转换后文件路径,fileExt:后缀
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 13:46
     */
    public static void ppt2pdf(String source, String target,String fileExt) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getPPTLicense()) {
            return;
        }
        try {
            //验证路径
            try {
                if (!(new File(target).isDirectory())) {
                    new File(target).mkdirs();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            // 文件名字
            String fileName=source.substring(source.lastIndexOf("/")+1);
            //新建一个空白pdf文档
            File file = new File(target+"/"+(fileName.replace(fileExt,"pdf")));
            //输入pdf路径
            Presentation pres = new Presentation(source);
            FileOutputStream fileOS = new FileOutputStream(file);
            pres.save(fileOS, SaveFormat.Pdf);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * @description: doc转pdf
     * @params:  source:源文件地址,target:转换后文件路径,fileExt:后缀
     * @return:
     * @author: liuhm
     * @Date: 2019/10/10 13:46
     */
    public static void doc2pdf(String source, String target,String fileExt) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getDocLicense()) {
            return;
        }
        try {
            //新建一个空白pdf文档
            try {
                if (!(new File(target).isDirectory())) {
                    new File(target).mkdirs();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            // 文件名字
            String fileName=source.substring(source.lastIndexOf("/")+1);
            // 输出路径
            File file = new File(target+"/"+(fileName.replace(fileExt,"pdf")));
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(source);
            doc.save(os, com.aspose.words.SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}