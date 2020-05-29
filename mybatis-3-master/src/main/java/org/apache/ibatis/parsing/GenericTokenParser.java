/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.parsing;

/**
 * @author Clinton Begin
 * 通用Token解析器
 */
public class GenericTokenParser {

  /**
   * 开始的Token字符串
   */
  private final String openToken;
  /**
   * 结束的Token字符串
   */
  private final String closeToken;

  private final TokenHandler handler;

  public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
    this.openToken = openToken;
    this.closeToken = closeToken;
    this.handler = handler;
  }

  public String parse(String text) {
    if (text == null || text.isEmpty()) {
      return "";
    }
    // search open token 寻找开始的Token位置
    int start = text.indexOf(openToken);
    if (start == -1) { //找不到Index，则直接返回
      return text;
    }
    char[] src = text.toCharArray();
    int offset = 0; //起始查找位置
    final StringBuilder builder = new StringBuilder();
    StringBuilder expression = null;// 匹配到 openToken 和 closeToken 之间的表达式
    while (start > -1) {
      //转义字符
      if (start > 0 && src[start - 1] == '\\') {
        // this open token is escaped. remove the backslash and continue.
        builder.append(src, offset, start - offset - 1).append(openToken);
        offset = start + openToken.length();
      //非转义字符
      } else {
        // found open token. let's search close token.
        if (expression == null) {
          expression = new StringBuilder();
        } else {
          expression.setLength(0);
        }
        //添加offset和openToken之间的内容，到builder中
        builder.append(src, offset, start - offset);
        //修改offset的位置
        offset = start + openToken.length();
        //寻找结束的 closeToken 的位置
        int end = text.indexOf(closeToken, offset);
        while (end > -1) {
          if (end > offset && src[end - 1] == '\\') {
            // this close token is escaped. remove the backslash and continue.
            expression.append(src, offset, end - offset - 1).append(closeToken);
            offset = end + closeToken.length();
            end = text.indexOf(closeToken, offset);
          } else {
            expression.append(src, offset, end - offset);
            break;
          }
        }
        //拼接内容
        if (end == -1) {
          // close token was not found.
          // closeToken未找到，直接拼接
          builder.append(src, start, src.length - start);
          //修改offset
          offset = src.length;
        } else {
          //closeToken 找到，将 expression 提交给 handler 处理 ，并将处理结果添加到 builder 中
          builder.append(handler.handleToken(expression.toString()));
          offset = end + closeToken.length();
        }
      }
      start = text.indexOf(openToken, offset);
    }
    if (offset < src.length) {
      builder.append(src, offset, src.length - offset);
    }
    return builder.toString();
  }
}
