package com.atguigu.springcloud.create_designmode.service.builder;

public class HtmlBuilder {
    private HeadingBuilder headingBuilder = new HeadingBuilder();
//    private HrBuilder hrBuilder = new HrBuilder();
//    private ParagraphBuilder paragraphBuilder = new ParagraphBuilder();
//    private QuoteBuilder quoteBuilder = new QuoteBuilder();

    public String toHtml(String markdown) {
        StringBuilder buffer = new StringBuilder();
        if (markdown.startsWith("#")) {
            buffer.append(headingBuilder.buildHeading(markdown)).append('\n');
        }else {
            buffer.append(markdown).append('\n');
        }
        return buffer.toString();
    }
}