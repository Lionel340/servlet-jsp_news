<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>发表新闻</title>
    <link rel="stylesheet" href="../../bootstrap-4.3.1-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<%--header--%>
<%@include file="header.jsp"%>
<div class="container-fluid pt-5">
    <div class="row">
        <%--侧边导航栏--%>
        <%@include file="nav.jsp"%>
        <main class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div class="d-flex flex-column pt-3 pb-5 mb-3">
                <form method="post" action="${pageContext.request.contextPath}/admin/manage/publish">
                    <input type="text" maxlength="50" placeholder="请输入标题" class="w-100 pl-3 mt-3 mb-3 title" name="title" />
                    <div id="contentEidtor"></div>
                    <textarea name="content" id="content" style="width: 100%;height: 75px"></textarea>
                    新闻分类：
                    <label>
                        <select class="mt-4" name="cate">
                            <c:forEach items="${requestScope.allCate}" var="cate">
                                <option value="${cate.id}">${cate.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <br />
                    <button type="submit" name="method" value="publish" class="mt-3 btn btn-danger">发表</button>
                </form>
            </div>
        </main>
    </div>
</div>
<script src="../../js/jquery.min.js"></script>
<script src="../../bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="../../wangEditor/wangEditor.min.js"></script>
<script>
    const E = window.wangEditor;
    const editor = new E('#contentEidtor');
    const $text1 = $('#content');
    editor.customConfig.onchange = function(html) {
        $text1.val(html)
    };
    editor.create();
    $text1.val(editor.txt.html())
</script>
</body>
</html>