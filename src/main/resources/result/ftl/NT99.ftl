<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>身障申請書</title>
  <link rel="stylesheet" href="${cssPath!}/pdf-styles.css" />

	<#import "FunctionUtil.ftl" as func/>
	<style type="text/css">
		@page {
			size: 1080px 1510px;
		}
	</style>
</head>
<body>

<div class="header"><a href="https://www.citibank.com.tw/" class="logo--hdL"></a> <a href="https://www.citibank.com.tw/" class="logo--hdR"></a> <a href="javascript:;" class="navSwitch" id="navSwitch"><span class="lines"></span></a></div>

<div class="main_body">
  <h3>減免跨行提款手續費申請書(線上申請專用)</h3>
  <div class="title">
    客戶基本資料
  </div>
  <div class="text">
    <p>申請日期：中華民國${year!} 年${month!} 月${day!} 日
    </p>
    <p>
      戶名： ${name!}
    </p>
    <p>
      身心障礙手冊/證明有效期限：${dateEnd!}
    </p>
    <p>
      電子郵件： ${email!}
    </p>
    <p>
      手機號碼：${phone!}
    </p>
    <p>
      <img src="${imgPath!}/icon1.png" style="width: 18px;" alt=""/>  本人以閱讀並同意服務條款：申請人簽章：
    </p>
    <p>
      <img src="${imgPath!}/icon1.png" style="width: 18px;" alt=""/>   本人以閱讀並同意個人資料運用條款：申請人簽章：
    </p>
    <p>
      <img src="${imgPath!}/icon1.png" style="width: 18px;" alt=""/>   本人已詳閱本同意書，並同意身心障礙者身心障礙資料線上驗證服務之告知事項：申請人簽章：
    </p>
  </div>
  <div class="title">
    服務條款
  </div>
  <div class="text">
    <p>申請人領有身心障礙證明/手冊，茲向花旗(台灣)銀行(以下稱「本行」)申請以花旗金融卡在國內其他金融機構自
      動櫃員機(ATM)跨行提領新臺幣現金時，得享每月3次免跨行提款手續費之優惠。申請人瞭解並同意下列事項：</p>

    <ul class="ul-style">
      <li data-list-style="一.">每月3次減免跨行提款手續費係以日曆月計算，每月3次以各交易日之提款次數累計，不併入其他手續費優惠計算次數，但當月未使用之減免次數不能累計至下月使用。</li>
      <li data-list-style="二.">本申請書之跨行提款手續費優惠不以特定之新臺幣活期存款帳戶為限，若申請人於本行有多個新臺幣活期存款帳戶，則所有帳戶併同計算手續費優惠次數。</li>
      <li data-list-style="三.">花旗(台灣)銀行得以立即減免或退費方式減免跨行提款手續費；若因銀行行政作業而以退費方式辦理時，當次交易仍會收取跨行提款手續費，已收取之手續費將於當月返還至申請人之活期存款帳戶。</li>
      <li data-list-style="四.">本服務條款為開戶總約定書之補充約定，其餘未盡事宜悉依開戶總約定書辦理。</li>
      <li data-list-style="五.">本手續費優惠優先於其他手續費優惠進行折抵。</li>
      <li data-list-style="六.">申請結果將於次日發送至您所留存的電子郵件信箱，敬請留意收信。</li>
      <li data-list-style="七.">本服務將透過聯徵中心向衛生福利部驗證身心障礙者之身份，目前有５個縣市在身心障礙手冊部分尚未採用衛生福利部中央系統處理，因此可能有部分持身心障礙手冊者無法驗證成功，若有前述情事時，申請人得透過當時本行所提供之其他受理方式提出申請。如無法透過聯徵中心驗證成功。您仍可透過本行各分行辦理。</li>
    </ul>
  </div>
  <div class="title">個人資料運用條款</div>
  <div class="text">
    <p>申請人聲明已受花旗(台灣)銀行(以下簡稱「花旗銀行」)依個人資料保護法規定所為之告知(詳見https://www.citibank.com.tw/global_docs/chi/pressroom/infoprotect.pdf)並已充分知悉，確認花旗銀行、其委託之第三人及其所告知對申請人個人資料利用對象，得依前揭告知內容或法令許可範圍內蒐集、處理、國際傳輸及利用申請人之個人資料。申請人為申請自動櫃員機(ATM)跨行提款手續費減免，授權並同意花旗銀行得向財團法人金融聯合徵信中心及衛生福利部驗證申請人身心障礙證明及有效期限等個人資料，並同意花旗銀行得保留所蒐集及驗證之相關資料。</p>
    <ul class="ul-style">
      <li data-list-style="一.">蒐集之目的： <br/>
        受理身心障礙者網路申請ATM跨行提款交易手續費減免。
      </li>
      <li data-list-style="二.">蒐集之個人資料類別：姓名、身分證字號、身心障礙證明及其有效期限。
      </li>
      <li data-list-style="三.">
        個人資料利用之期間、對象、地區、方式：

        <ul class="ul-style">
          <li data-list-style="(一)"> 期間：因執行業務所必須及依法令規定應為保存之期間。</li>
          <li data-list-style="(二)"> 對象：花旗銀行(含受花旗銀行委託處理事務之委外機構)、財團法人金融聯合徵信中心、衛生福利部、依法有調查權之機關或金融監理機關。</li>
          <li data-list-style="(三)"> 地區：上述對象所在之地區。</li>
          <li data-list-style="(四)"> 方式：合於法令規定之利用方式。</li>
        </ul>
      </li>
      <li data-list-style="四.">
        依據個資法第3條規定，申請人就個人資料得行使之權利及方式：

        <ul class="ul-style">
          <li data-list-style="(一)"> 得向花旗銀行行使之權利：
            <ol>
              <li>請求查詢、請求瀏覽或請求製給複製本。</li>
              <li>請求補充或更正。</li>
              <li>請求停止蒐集、處理或利用及請求刪除。</li>
            </ol>
          </li>
          <li data-list-style="(二)"> 行使權利之方式：以書面或其他日後可供證明之方式。</li>
        </ul>
      </li>
      <li data-list-style="五.">
        不提供個人資料所致權益之影響： <br/>
        申請人若未能提供相關個人資料時，花旗銀行將可能延遲或無法進行必要之驗證等作業，因此將不能或無法即時受理申請人之申請或提供相關服務。

      </li>
    </ul>
    <p>申請人已了解花旗銀行個人資料蒐集處理及利用告知事項，並同意花旗銀行得向相關機構驗證並蒐集(保留)、處理及利用申請人身心障礙相關資料。 </p>
  </div>

  <div class="title">
    身心障礙者資料線上驗證服務蒐集個人資料告知事項暨個人資料提供同意書
  </div>
  <div class="text">
    <p>
      蒐集個人資料告知事項： <br/>
      金融監督管理委員會及財團法人金融聯合徵信中心(以下合稱前揭機構)為遵守個人資料保護法規定，在您提供個人資料授權金融機構向財團法人金融聯合徵信中心申請身心障礙者身心障礙資料線上驗證服務前，依法告知下列事項：
    </p>
    <ul class="ul-style">
      <li data-list-style="一.">
        金融監督管理委員會為協助身心障礙者參與社會，向衛生福利部社會及家庭署申請使用身心障礙者資料，並指定財團法人金融聯合徵信中心擔任金融機構與衛生福利部社會及家庭署間之資料傳遞介接平台。
      </li>
      <li data-list-style="二.">
        前揭機構因金融機構受理身心障礙者網路申請ATM跨行交易手續費減免業務之特定目的而獲取您下列個人資料類別：姓名、身分證號、身心障礙證明有效期限。
      </li>
      <li data-list-style="三.">
        前揭機構將依個人資料保護法及相關法令之規定，蒐集、處理及利用您的個人資料。
      </li>
      <li data-list-style="四.">
        前揭機構將於蒐集目的之存續期間合理利用您的個人資料。
      </li>
      <li data-list-style="五.">
        前揭機構僅於中華民國領域內利用您的個人資料。
      </li>
      <li data-list-style="六.">
        您可依個人資料保護法第3條規定，就您的個人資料檢附申請書，向金融監督管理委員會或經其指定之財團法人金融聯合徵信中心行使之下列權利：
        <br/>
        (一)查詢或請求閱覽。             <br/>
        (二)請求製給複製本。             <br/>
        (三)請求補充或更正。             <br/>
        (四)請求停止蒐集、處理及利用。             <br/>
        (五)請求刪除。             <br/>
        您如申請第(四)、(五)項，前揭V機構將於終止提供金融機構線上申請驗證您身心障礙者之身分，若因此導致您網路申請ATM跨行提款交易手續費減免優惠的權益產生減損時，不負相關賠償責任。
      </li>
      <li data-list-style="七.">
        若您未提供正確之個人資料，前揭機構將無法為您提供特定目的之相關業務。
      </li>
      <li data-list-style="八.">
        前揭機構因業務需要而委託其他機關處理您的個人資料時，將善盡監督之責。
      </li>
      <li data-list-style="九.">
        您瞭解此一同意書符合個人資料保護法及相關法規之要求，且同意前揭機構日後取出查驗。
      </li>
    </ul>
  </div>
  <div class="title">
    個人資料之同意提供
  </div>
  <div class="text">
    <ul class="ul-style">
      <li data-list-style="一.">
        本人已充分知悉上述告知事項。

      </li>
      <li data-list-style="二.">
        本人同意前揭機構蒐集、處理及利用本人之個人資料，並同意其他公務機關請求行政協助目的之提供。
      </li>
    </ul>
  </div>
</div>
<div class="ft" id="ft">
  <div class="wrap1">
    <div class="ft__left"><a target="_blank" href="http://www.citibank.com/us/index.htm" class="citiLogo">花旗全球網</a></div>
    <div class="ft__right"><a target="_blank" href="https://www.citibank.com.tw/global_docs/chi/info/privacy.htm">客戶資料安全保護政策</a>
      <br/><a target="_blank" href="https://www.citibank.com.tw/global_docs/chi/info/disclaim.htm">讀者重要訊息</a>
      <p>Copyright © 2018Citigroup</p>
    </div>
  </div>
</div>

</body></html>
