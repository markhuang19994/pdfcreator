<#macro nt99_style>
<#assign imagePath = imgPath!>
<style type="text/css">
html,body,div,span,table,caption,
tbody,tfoot,thead,tr,th,td,
font,img,small,strong,sub,sup,
p,a,dl,dt,dd,ol,
ul,li,form,label {
  vertical-align: baseline;
  font-family: inherit;
  font-weight: inherit;
  font-style: inherit;
  font-size: 100%;
  outline: 0;
  padding: 0;
  margin: 0;
  border: 0
}

ol,ul {
  list-style: none;
}

table {
  border-collapse: separate;
  border-spacing: 0
}

:focus {
  outline: none;
}

html * {
  max-height: 1000000px;
  -webkit-text-size-adjust: 100%
}

a {
  color: #535353;
  text-decoration: none
}

a,area {
  outline: none;
  hlbr: expression(this.onFocus=this.blur())
}

img {
  vertical-align: top;
}

.ft:after {
  display: block;
  content: '';
  height: 0;
  font-size: 0;
  clear: both;
  visibility: hidden;
}


body {
  font: normal 15px/18px 'Microsoft JhengHei';
  color: #000;
  background: #fff;
}

input,select,textarea {
  font: normal 15px/18px 'Microsoft JhengHei';
  padding: 0;
  margin: 0;
  vertical-align: middle
}

a {
  color: #000;
  text-decoration: none
}

div,a,input,p {
  font-family: 'Microsoft JhengHei'
}

html,body,.part1,.part2,.part3,.part4,
.part5,.kana,.share,.btmBanner,.notice,.warnning,
.ft {
  width: 100%
}

.header,.nav {
  background: #1da4db;
  background: -webkit-linear-gradient(top, #1da4db 0%, #1990cf 25%, #0f6fb4 50%, #07559e 75%, #003e7f 100%);
  background: -webkit-linear-gradient(top, #1da4db 0%, #1990cf 25%, #0f6fb4 50%, #07559e 75%, #003e7f 100%);
  background: linear-gradient(to bottom, #1da4db 0%, #1990cf 25%, #0f6fb4 50%, #07559e 75%, #003e7f 100%);
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#1da4db', endColorstr='#003e7f', GradientType=0)
}

.header {
  position: relative;
  left: 0;
  top: 0;
  height: 90px;
  width: 100%;
  z-index: 100
}

.header.nofixed{
  position: relative;
}

.header .logo--hdL {
  display: block;
  position: absolute;
  top: 38px;
  left: 22px;
  width: 128px;
  height: 29px;
  background: url(${imgPath!}/logo_citi-1.png) 0 0 no-repeat;
}

.header .logo--hdR {
  display: block;
  position: absolute;
  top: 12px;
  right: 23px;
  width: 96px;
  height: 56px;
  background: url(${imgPath!}/logo_citi-2.png) 0 0 no-repeat;
}

.ft {
  font-size: 13px;
  line-height: 18px;
  vertical-align: top
}

.ft .wrap1 {
  padding: 10px 0;
  border-top: 4px solid #004785;
  overflow: hidden
}

.ft .ft__left {
  float: left;
  vertical-align: top
}

.ft .ft__right {
  float: right;
  vertical-align: top;
  text-align: right;
  color: #000
}

.ft .ft__right a {
  margin-bottom: 3px
}

.ft a {
  position: relative;
  color: #004784;
  display: inline-block
}

.ft a:after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: #004784
}

.ft .citiLogo {
  background: url(${imgPath!}/logo_citi-ft.png) 50% 0 no-repeat;
  padding-top: 33px
}

.mShow {
  display: none
}

.pcShow {
  display: block
}

@media screen and (max-width: 767px) {
  .mShow {
    display: block
  }
  .pcShow {
    display: none
  }
}

.modal-header .close {
  font-size: 40px;
  line-height: 20px;
}

.mybox_cont .modal-content{
  border-radius:0;
}

.mybox_cont .modal-body h2{
  font-size: 18px;
  text-align: center;color: #282828;
  padding: 0;
  margin: 0;
  margin-bottom: 20px;
}

.mybox_cont .modal-body h2 p{
  font-size: 14px;
  color: #767676;
}
.mybox_cont .modal-footer {
  padding: 0;
  text-align: center;
}

.mybox_cont .modal-footer .btn+.btn {
  margin-bottom: 0;
  margin-left: 0px;
}

.mybox_cont .modal-footer .btn{
  font-size: 15px;
  width:50%;
  border: none;
  border-radius: 0;
  float: left;
  padding:15px 10px;
}

.mybox_cont .modal-footer  .btn-default{
  color: #0076c0;
}

.modal-dialog {
  z-index: 1040;
  position: fixed;
  top: 20px;
  bottom: 20px;
  left: calc(50% - 45% - 7.5px);
  width: calc(90%);
}

@media (min-width: 768px){
  .modal-dialog {
    left: calc(50% - 450px);
    width: 900px;
  }

  #myModal.mybox_cont .modal-dialog,#ErrModal.mybox_cont .modal-dialog,#myModal_time.mybox_cont .modal-dialog{
    max-width: 730px;
    left: calc(50% - 375px);
    top: 15%;
    bottom: 15%;
  }

}

#myModal.mybox_cont .modal-dialog .modal-title,#ErrModal.mybox_cont .modal-dialog .modal-title,#myModal_time.mybox_cont .modal-dialog .modal-title{
  text-align: center;
  font-size: 20px;
  color: #0076c0 ;
}

.modal-content {
  height: 100%;
}

.modal-header{
  height: 56px;
}

.modal-body{
  height: calc(100% - 56px);
  overflow-y: auto;
}

body {
  background: #f6f6f6;
  color: #666;
}

label {
  font-weight: normal;
}

.pc_show-row {
  display: none !important;
}

@media (min-width: 768px) {
  .pc_show-row {
    display: table-row !important;
  }
}

.form-control[disabled] .form-control, .form-control[readonly] .form-control, fieldset[disabled] .form-control {
  background-color: #fff;
  color: #666;
}

.wrap {
  width: 1000px;
  padding: 0px 15px;
  background: #fff;
  margin: 0 auto;
  padding: 0px;
}

.main_body {
  width: 780px;
  margin: 0 auto;
  padding: 45px 0;
}

.main_body h3 {
  text-align: center;
  font-size: 42px;
  font-weight: inherit;
  color: #00bdf2;
  margin: 80px 0 60px;
  position: relative;
  padding-bottom: 15px;
}

.main_body h3:after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  background-image: url(${imgPath!}/line.png);
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 3px;
}

.main_body p {
  font-size: 22px;
  line-height: 25px;
  margin-bottom: 15px;
}

.main_body .link_cont {
  background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #1b8ecf), to(#01BCF1));
  background: -webkit-linear-gradient(bottom, #1b8ecf 0, #01BCF1 100%);
  background: -moz-linear-gradient(bottom, #1b8ecf 0, #01BCF1 100%);
  background: -o-linear-gradient(bottom, #1b8ecf 0, #01BCF1 100%);
  background: linear-gradient(0deg, #1b8ecf 0, #01BCF1 100%);
  width: 300px;
  margin: 100px auto;
  text-align: center;
  padding: 35px 25px;
  -webkit-border-radius: 7px;
  -moz-border-radius: 7px;
  border-radius: 7px;
  color: #fff;
  border: 1px solid #076ba7;
  -webkit-box-shadow: 0px 4px 0px #073057;
  -moz-box-shadow: 0px 4px 0px #073057;
  box-shadow: 0px 4px 0px #073057;
}

.main_body .link_cont img {
  margin-bottom: 20px;
}

.main_body .link_cont .title {
  font-size: 20px;
  margin-bottom: 15px;
  color: #fff;
}

.main_body .link_cont a {
  color: #fff;
  display: inline-block;
}

.main_body .link_cont a:hover {
  text-decoration: none;
  text-shadow: 2px 2px 20px #ecf1f3;
}

.main_body .link_cont p {
  font-size: 20px;
  margin-bottom: 0;
}

.main_body .link_cont p span {
  text-decoration: underline;
}

.main_body .title {
  text-align: center;
  margin-bottom: 40px;
  color: #004785;
  font-size: 26px;
}

.main_body .title.icon1:before {
  content: '';
  background-image: url(${imgPath!}/s_icon1.png);
  width: 29px;
  height: 29px;
  display: inline-block;
  vertical-align: middle;
}

.main_body .title.icon2:before {
  content: '';
  background-image: url(${imgPath!}/s_icon2.png);
  width: 29px;
  height: 29px;
  display: inline-block;
  vertical-align: middle;
}

.main_body .title.icon3:before {
  content: '';
  background-image: url(${imgPath!}/s_icon3.png);
  width: 29px;
  height: 29px;
  display: inline-block;
  vertical-align: middle;
}

.main_body .title.icon4:before {
  content: '';
  background-image: url(${imgPath!}/s_icon4.png);
  width: 29px;
  height: 29px;
  display: inline-block;
  vertical-align: middle;
}

.btnWp {
  padding: 75px 0;
  text-align: center;
}

.modal-body p {
  font-size: 16px;
  line-height: 25px;
  margin-bottom: 10px;
}

.modal-body p .pl {
  padding-left: 20px;
  display: inline-block;
}

.modal-body .bottom_text {
  padding: 25px 0;
}

.modal-body .bottom_text a {
  color: #047ab7;
}

.main_btn {
  margin: 0 10px;
  display: inline-block;
  vertical-align: middle;
  padding: 15px 50px;
  color: #fff;
  background: #00bdf2;
  font-size: 20px;
  border: 0;
  -webkit-border-radius: 7px;
  -moz-border-radius: 7px;
  border-radius: 7px;
}

.main_btn:hover {
  color: #fff;
  background: #00bdf2;
  text-decoration: none;
}

.main_btn.dsb {
  background: #ccc;
}

.step {
  border: 1px solid #004785;
  padding: 30px 60px;
  -webkit-border-radius: 7px;
  -moz-border-radius: 7px;
  border-radius: 7px;
}

.step .end {
  padding: 90px 0;
}

.step .end h4 {
  font-size: 20px;
  color: #000;
}

.step .end p {
  font-size: 20px;
}

.step .form-group {
  display: table;
  width: 100%;
  margin-bottom: 20px;
}

.step .form-group .control-label, .step .form-group input, .step .form-group > span, .step .form-group > .check_style {
  display: table-cell;
  vertical-align: middle;
  font-size: 20px;
}

.step .form-group > .check_style {
  padding-top: 0;
}

.step .form-group > .check_style label.css-label {
  margin-bottom: 0 !important;
}

.step .form-group .control-label {
  text-align: left;
  font-size: 20px;
  padding-left: 15px;
  position: relative;
}

.step .form-group .control-label:before {
  content: '';
  background-image: url(${imgPath!}/do.png);
  width: 9px;
  height: 100%;
  display: inline-block;
  vertical-align: middle;
  margin-right: 5px;
  left: 0;
  margin: 0 auto;
  position: absolute;
  background-repeat: no-repeat;
  top: 0;
  background-position: center;
}

.step .form-group:last-child {
  margin-bottom: 0;
}

.step .red {
  color: #ff0101;
  font-size: 14px;
}

.step ol {
  list-style: decimal;
  padding-left: 20px;
}

.step ol li {
  font-size: 20px;
  line-height: 20px;
  margin-bottom: 25px;
  text-align: left;
}

.step ol li span, .step ol li input {
  display: inline-block;
}

.step ol li input {
  max-width: 200px;
}

.step ol li a {
  text-decoration: underline;
  color: #007fb2;
}

.step ol li:last-child {
  margin-bottom: 0;
}

.step-item {
  display: table;
  margin-bottom: 35px;
  border: 1px solid #00bdf2;
  width: 100%;
}

.step-item .left, .step-item .text {
  display: table-cell;
}

.step-item .left {
  -webkit-box-shadow: 2px 0px 5px #8c8a8a;
  -moz-box-shadow: 2px 0px 5px #8c8a8a;
  box-shadow: 2px 0px 5px #8c8a8a;
  font-size: 30px;
  width: 57px;
  vertical-align: middle;
  background: #00bdf2;
  color: #fff;
  text-align: center;
}

.step-item .text {
  background: #effaff;
  padding: 25px;
}

.step-item .text b {
  font-size: 20px;
  margin-bottom: 10px;
  display: inline-block;
  line-height: 25px;
}

.step-item .text ul {
  list-style: inherit;
  padding-left: 20px;
}

.step-item .text ul li {
  margin-bottom: 10px;
  font-size: 18px;
  line-height: 25px;
}

.step-item .text ul li:last-child {
  margin-bottom: 0;
}

.step-item .text ul li a {
  color: #ff0000;
  text-decoration: underline;
}

.main_step {
  padding: 45px 0;
  text-align: center;
  background: #f9f9f9;
  -webkit-box-shadow: 0px 6px 12px #d4d2d2;
  -moz-box-shadow: 0px 6px 12px #d4d2d2;
  box-shadow: 0px 6px 12px #d4d2d2;
}

.main_step ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: inline-block;
}

.main_step ul li {
  display: inline-block;
  width: 160px;
  height: 69px;
  background-image: url(${imgPath!}/top_step.png);
  background-position-y: bottom;
}

.main_step ul li.step_icon1 {
  background-position: left bottom;

}

.main_step ul li.step_icon2 {
  background-position: -160px bottom;
}

.main_step ul li.step_icon3 {
  background-position: -320px bottom;
}
.main_step ul li.step_icon4 {
  background-position: -480px bottom;
}
.main_step ul li.step_icon5 {
  background-position: -640px bottom;
}
.main_step ul li.active {
  background-position-y: top;
}

.check_style {
  padding: 15px 0 0;
}

.check_style input[type=checkbox].css-checkbox, .check_style input[type=radio].css-checkbox {
  position: absolute;
  z-index: -1000;
  left: -1000px;
  overflow: hidden;
  clip: rect(0 0 0 0);
  height: 1px;
  width: 1px;
  margin: -1px;
  padding: 0;
  border: 0;
}

.check_style input[type=checkbox].css-checkbox + label.css-label, .check_style input[type=checkbox].css-checkbox + label.css-label.clr,
.check_style input[type=radio].css-checkbox + label.css-label, .check_style input[type=radio].css-checkbox + label.css-label.clr {
  padding-left: 37px;
  height: 31px;
  font-weight: bold;
  display: inline-block;
  line-height: 31px;
  background-repeat: no-repeat;
  background-position: 0 0;
  font-size: 18px;
  vertical-align: middle;
  cursor: pointer;
}
.check_style input[type=checkbox].css-checkbox:checked + label.css-label,
.check_style input[type=radio].css-checkbox:checked + label.css-label {
  background-position: 0 -31px;
}
.check_style input[type=checkbox].css-checkbox:disabled + label.css-label,
.check_style input[type=radio].css-checkbox:disabled + label.css-label {
  background-position: 0 0px;
}
.check_style label.css-label {
  background-image: url(${imgPath!}/checkbox.png);
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  margin-right: 15px;
  margin-bottom: 15px;
}

@media (min-width: 768px) {
  .tx_textarea {
    margin-top: -10px;
    margin-bottom: 15px;
    padding-left: 30px;
    width: 100%;
  }
  .tx_textarea-cont {
    display: inline-block;
    vertical-align: middle;
    text-align: left;
  }
  .tx_textarea-cont textarea {
    margin-bottom: 0;
  }
}

</style>
</#macro>