# pdfcreator
=============

1.功能: 
   >把一般的HTML格式化成FTL    
   >FTL加入key-value生成PDF    
   >當FTL及其相關檔案發生更動時重新產出PDF    

2.使用方式:   
   >在resource/result/source_html資料夾放入source.html，輸入-g參數即可將source.html格式化成FTL    
   >在data/data.json可以指定ftl需使用的key-value(對應ftl內的${key})，其中必須加入cssPath指定css目錄與imagePath指定image目錄
    
3.注意事項:    
   >image資源必須放在images資料夾底下，source.html內需使用&lt;img src='./images/xxx.jpg'/&gt;   
   >css資源必須放在css資料夾底下，source.html內需使用&lt;link href='./css/ooo.css'/&gt;   
   >source.html轉換後input checkbox會變成圖片資源，相關圖片放置在本專案"others"資料夾中    
   
