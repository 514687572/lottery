<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/reset.css">
    <link rel="stylesheet" href="../css/cashOut.css">
    <title>cashOut</title>
</head>

<body>
    <header>
        <nav class="navContainer">
            <img src="../images/casino_logo_1.svg" alt="logo" id="backToHome"/>
            <div id="headRight">
                <div id="LanguageBox" class="langCut">
                    <div id="inputBox">
                        <img src="../images/Singapore.png" alt="" id="guoqiBox" />中文
                    </div>
                    <ul id="language">
                        <li id="Ch">
                            <i></i>
                            <ctx:message code="chinese" />
                        </li>
                        <li id="En">
                            <i></i>English</li>
                    </ul>
                </div>
                <img src="../images/down.png" alt="down" id="changeLanguage" class="langCut" style="width:20px;height:20px;" />
            </div>
        </nav>
    </header>
    <section>
        <div id="title">
            GOS提现
            <span id="back">返回</span>
        </div>
        <div class="cashContain">
            <span>可用GOS：</span><span id="userGos"></span>
        </div>
        <div class="cashContain">
            <span>GOS提现：</span><input id="sumInput" placeholder="请输入提现的GOS数值" type=text; /><span></span>
        </div>
        <p>提现GOS(100-50000),1GOS=1元,每笔手续费2元</p>
        <div class="cashContain">
            <span>提现所得：</span><span id="outMoney"></span>
        </div>
        <div class="cashContain">
            <span>开户银行：</span>
            <select name="" id="bank">
                <option value='BOC,中国银行'>中国银行</option>
                <option value='ICBC,中国工商银行'>中国工商银行</option>
                <option value='CCB,中国建设银行'>中国建设银行</option>
                <option value='ABC,中国农业银行'>中国农业银行</option>
                <option value='CMB,招商银行'>招商银行</option>
                <option value='PSBC,中国邮政储蓄银行'>中国邮政储蓄</option>
                <option value='TICIC,中信银行'>中信银行</option>
                <option value='CEB,中国光大银行'>中国光大银行</option>
                <option value='HXB,华夏银行'>华夏银行</option>
                <option value='CGB,广发银行'>广发银行</option>
                <option value='PAB,平安银行'>平安银行</option>
                <option value='CIB,兴业银行'>兴业银行</option>
                <option value='SPDB,浦发银行'>浦发银行</option>
                <option value='BOB",北京银行'>北京银行</option>
                <option value='EGB,恒丰银行'>恒丰银行</option>
                <option value='CZB,浙商银行'>浙商银行</option>
                <option value='CBHB,渤海银行'>渤海银行</option>
                <option value='HSB,徽商银行'>徽商银行</option>
                <option value='SRCB,上海农村商业银行'>上海农村商业银行</option>
                <option value='BOS",上海银行'>上海银行</option>
                <option value='NBCB,宁波银行'>宁波银行</option>
                <option value='HZCB,杭州商业银行'>杭州商业银行</option>
                <option value='WFCCB,潍坊银行'>潍坊银行</option>
                <option value='BEA,东亚银行'>东亚银行</option>
                <option value='NCB,南洋商业银行'>南洋商业银行</option>
                <option value='HSBANK,恒生银行'>恒生银行</option>
                <option value='SHBB,上海商业银行'>上海商业银行</option>
                <option value='NJCB,南京银行'>南京银行</option>
                <option value='QDCCB,青岛银行'>青岛银行</option>
                <option value='TJCB,天津银行'>天津银行</option>
                <option value='HBB,河北银行'>河北银行</option>
                <option value='BOCD,成都银行'>成都银行</option>
            </select>
        </div>
        <div class="cashContain">
            <span>银行卡号：</span><input id="carInput" placeholder="请输入银行卡号" type=text; /><span></span>
        </div>
        <div class="cashContain">
            <span>开卡姓名：</span><input id="nameInput" placeholder="请输入持卡人姓名" type=text; /><span></span>
        </div>
        <div id="formBtnContain">
                <button id="formBtn">提交</button>
        </div>
    </section>
    <script src="../js/jquery.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>
</body>

</html>