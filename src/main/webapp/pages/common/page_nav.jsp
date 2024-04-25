<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--分页-->
<div id="page_nav">

    <!--页码大于1显示-->
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1&pageSize=${requestScope.page.pageSize}" class="prev">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo - 1 }&pageSize
        =${requestScope.page.pageSize}"  class="prev">上一页</a>
    </c:if>

<!-- 分页功能分析
 功能要求：分页模块中页码1 2 【3】 4 5 的显示要连续显示五页，并且可以跳转
   + 情况1 总页码的数量小于等于5 页码的范围是：1~5
   1页： 1
   2页： 1 2
   3页： 1 2 3
   4页： 1 2 3 4
   5页： 1 2 3 4 5
   + 情况2 页码的总数量大于5 假设一共10页
     小情况1：当前页码为前3页 1 2 3  页码范围：1~5
     【1】 2 3 4 5
      1 【2】 3 4 5
      1 2 【3】 4 5
     小情况2：当前页码为最后3页  页码范围：总页码-4~总页码
       6 7 【8】 9 10
       6 7 8 【9】 10
       6 7 8 9 【10】
     小情况3：小情况1和2以外的  页码范围：当前页码-2~ 当前页码+2
       2 3 【4】 5 6
       3 4 【5】 6 7
       4 5 【6】 7 8
-->

    <c:choose>
        <%--总页数小于等于5--%>
        <c:when test='${requestScope.page.pageTotal <= 5}'>
            <%--遍历所有页数：1 ~ pageTotal--%>
            <c:forEach begin='1' end='${requestScope.page.pageTotal}' step='1' var='i'>
                <c:choose>
                    <%--当前页码时--%>
                    <c:when test='${i == requestScope.page.pageNo}'>
                        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo}&pageSize
                        =${requestScope.page.pageSize}"  class="prev">【${i}】</a>
                    </c:when>
                    <%--非当前页码时--%>
                    <c:otherwise>
                        <a href="${requestScope.page.url}&pageNo=${i}&pageSize=${requestScope.page.pageSize}" class="prev">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:when>
        <%--总页数大于等于5--%>
        <c:otherwise>
            <c:choose>
                <%--当前页码>=1 并且 <= 3--%>
                <c:when test='${requestScope.page.pageNo >= 1 && requestScope.page.pageNo <= 3}'>
                    <%--遍历所有页数：1 ~ 5--%>
                    <c:forEach begin='1' end='5' step='1' var='i'>
                        <c:choose>
                            <c:when test='${i == requestScope.page.pageNo}'>
                                <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo}&pageSize
                                =${requestScope.page.pageSize}" class="prev">【${i}】</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${requestScope.page.url}&pageNo=${i}&pageSize=${requestScope.page.pageSize}"
                                   class="prev">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>

                <%--当前页码为最后三页--%>
                <c:when test='${requestScope.page.pageNo >= requestScope.page.pageTotal - 2 && requestScope.page.pageNo <= requestScope.page.pageTotal}'>
                    <%--遍历所有页数：pageTotal-4 ~ pageTotal--%>
                    <c:forEach begin='${requestScope.page.pageTotal - 4}' end='${requestScope.page.pageTotal}' step='1' var='i'>
                        <c:choose>
                            <c:when test='${i == requestScope.page.pageNo}'>
                                <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo}&pageSize
                                =${requestScope.page.pageSize}" class="prev">【${i}】</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${requestScope.page.url}&pageNo=${i}&pageSize=${requestScope.page.pageSize}"
                                   class="prev">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>

                <%--上面以外--%>
                <c:otherwise>
                    <%--遍历所有页数：pageNo - 2 ~ pageNo + 2--%>
                    <c:forEach begin='${requestScope.page.pageNo - 2}' end='${requestScope.page.pageNo + 2 }' step='1' var='i'>
                        <c:choose>
                            <c:when test='${i == requestScope.page.pageNo}'>
                                <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo}&pageSize=${requestScope.page.pageSize}"class="prev">【${i}】</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${requestScope.page.url}&pageNo=${i}&pageSize=${requestScope.page.pageSize}"class="prev">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

    <%--页码小于最大页码时显示--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo + 1 }&pageSize
        =${requestScope.page.pageSize}" class="prev">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}&pageSize
        =${requestScope.page.pageSize}" class="prev">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.recordTotal}记录

    跳转到<input type="text" id="pageNo" name="pageNo" value="${requestScope.page.pageNo}"/>页
    每页显示<input type="text" id="pageSize" name="pageSize" value="${requestScope.page.pageSize}">条记录
    <input type="button" id="btnPage" value="确定">
</div>