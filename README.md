# pdfcreator
=============

1.功能: 
   >把一般的HTML格式化成FTL    
   >FTL加入key-value生成PDF    
   >當FTL及其相關檔案發生更動時重新產出PDF    

2.使用方式:   
   >在resource/result/source_html資料夾放入html(會自動抓最新的一個)，輸入-g參數即可將html格式化成FTL    
   >在data/data.json可以指定ftl需使用的key-value(對應ftl內的${key})   
   >格式化出的FTL將會放置在resource/ftl(如果source_html內無html檔可參照，則預設為source.ftl)，輸入-c參數即可產生PDF    
   >輸入參數-help可以看到額外設定功能(資源目錄、source_html目錄、ftl檔案路徑等設定)
    
3.資源配置:    
   >image資源預設放在images資料夾底下，source.html內需使用&lt;img src='./images/xxx.jpg'/&gt;   
   >css資源預設放在css資料夾底下，source.html內需使用&lt;link href='./css/ooo.css'/&gt;   
   >css內的image資源須放在css資料夾相對位置，如background: url(../image/my_image.jpg)    
   >data.json中可以加入cssPath指定css目錄與imagePath指定image目錄    
   >source.html轉換後input checkbox會變成圖片資源，相關圖片放置在本專案"others"資料夾中
   
4.執行方法:    
   >可以直接在ide匯入本專案執行     
   >windows用戶可以在other/execute/Windows資料夾下找到pdfc.exe    
   >mac用戶可以在other/execute/Linux資料夾下找到pdfc

5.執行方法2 for linux:    
   >在jar根目錄執行:    
      ```
        sudo ln -s $(pwd)/cpdf.sh /usr/bin/cpdf
      ```

6.PDF閱讀器:
   >☆本專案需搭配支援刷新的PDF閱讀器使用    
   >>windows:https://www.sumatrapdfreader.org/download-free-pdf-viewer.html/    
   >>mac:https://skim-app.sourceforge.io/    
   >>mac skim刷新相關設定:    
   >>>https://skim-app.sourceforge.io/manual/SkimHelp_14.html    
   >>>https://tex.stackexchange.com/questions/43057/macosx-pdf-viewer-automatic-reload-on-file-modification
   
   
