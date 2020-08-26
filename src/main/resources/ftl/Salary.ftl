<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html lang="zh-Hant-TW" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>PDF</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="${cssPath!}/style2.css" />

	<style type="text/css">
		@page {
			size: a4;
			margin: 0;
		}
	</style>
</head>
<body>

<div class="container">
    <div id="header-wrapper">
        <div class="inline-block" id="header-logo">
            <img src='${imagePath!}/logo.jpg' alt="none"   />
        </div>
        <div class="inline-block" id="header-title">
            <h1>網聯通訊股份有限公司</h1>
            <h1>Netlink Communication Corp.</h1>
        </div>
    </div>

    <div class="container">
        <div id="top-title">
            <div class="inline-block top-title-item">
                <span id="top-title-year">2019年(Y)</span>
            </div>
            <div class="inline-block top-title-item">
                <span id="top-title-month">3月(M)</span>
            </div>
            <div class="inline-block top-title-item">
                <span>薪 資 明 細 表</span>
            </div>
            <div class="inline-block top-title-item">
                <span>Salary Slip</span>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="top-title-line2">
            <div id="top-title-line2-department" class="inline-block">
                <span class="title"><span>部門</span><br/><span>Dept.</span></span>
                <span class="value">${departmentChineseName!}</span>
            </div>
            <div id="top-title-line2-employee-number" class="inline-block">
                <span class="title"><span>工 號</span><br/><span>Employee NO.</span></span>
                <span class="value">${employeeNumber!}</span>
            </div>
            <div id="top-title-line2-name" class="inline-block">
                <span class="title"><span>姓名</span><br/><span>Name.</span></span>
                <span class="value">${employeeChineseName!}</span>
            </div>
        </div>
    </div>

    <div class="container">
        <table id="salary-table">
            <thead>
            <tr>
                <th colspan="2"  style="background-color: #f2f2f2;">加 項</th>
                <th colspan="2" style="background-color: #cccccc;">扣 項</th>
                <th style="background-color: #f2f2f2;">實發金額</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td width="23%">薪資</td>
                <td width="15%">${adjustAbleSalary!}</td>
                <td width="26%">薪資所得稅</td>
                <td width="14%">${salariesAndWages!}</td>
                <td rowspan="13"></td>
            </tr>
            <tr>
                <td>職務加給</td>
                <td>${dutyBonus!}</td>
                <td>勞 保 費</td>
                <td>${laborInsurance!}</td>
            </tr>
            <tr>
                <td>工作加給</td>
                <td>${workBonus!}</td>
                <td>健 保 費</td>
                <td>${heathInsurance!}</td>
            </tr>
            <tr>
                <td>津 貼</td>
                <td>${allowance1!}</td>
                <td>福 利 金</td>
                <td>${welfare!}</td>
            </tr>
            <tr>
                <td>應稅折現</td>
                <td>${dutyRealization1!}</td>
                <td>健保補充保費</td>
                <td>${supplementaryPremium!}</td>
            </tr>
            <tr>
                <td>免稅折現1</td>
                <td>${freeDutyRealization1!}</td>
                <td>自提退休金</td>
                <td>${selfRecruitment!}</td>
            </tr>
            <tr>
                <td>免稅折現2</td>
                <td>${freeDutyRealization2!}</td>
                <td>其他扣款</td>
                <td>${otherDeduction1!}</td>
            </tr>
            <tr>
                <td>獎 金</td>
                <td>${financialIncentive1!}</td>
                <td>獎金所得稅</td>
                <td>${premiumIncomeTax!}</td>
            </tr>
            <tr>
                <td>應稅加班費</td>
                <td>${dutyOvertime!}</td>
                <td>加班所得稅</td>
                <td>${overtimeIncomeTax!}</td>
            </tr>
            <tr>
                <td>免稅加班費</td>
                <td>${freeDutyOvertime!}</td>
                <td>病假扣額</td>
                <td>${sickLeaveDeduction!}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>事假扣額</td>
                <td>${personalLeaveDeduction!}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>超休返還金額</td>
                <td>${preLeaveReturnAmount!}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>小計</td>
                <td>${payableTotal!}</td>
                <td>小計</td>
                <td>${shouldDeductionAmount!}</td>
                <td style="text-align: right">${netTotalAmount!}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <footer>
        <div class="container">
            <div id="footer-line1">
                <div id="footer-line1-recruitment" class="inline-block">
                    <span>公提退休金:</span>
                    <span class="value">${governmentPension!}</span>
                </div>
                <div id="footer-line1-bank-account" class="inline-block">
                    <span>銀行帳號:</span>
                    <span class="value">${bankAccount!}</span>
                </div>
            </div>
            <div id="footer-pay-day">
                <span>發放日期 Pay Day:  ${payDay!}</span>
            </div>
        </div>
    </footer>


</div>

</body>
</html>
