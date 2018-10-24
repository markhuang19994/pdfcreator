<#function underline input underlineLen align>
    <#assign span="<span style='display:  inline-block;border-bottom: .8px solid black;position: relative;bottom: 1px;width:"+(underlineLen?length*5.4)?string.number+"px;'>">
    <#assign p="<p style='position:relative;top: 1.5px;text-align:"+align+";font-weight:bold;font-size:14px;'>">
    <#assign endP="</p>">
    <#assign endSpan="</span>">
    <#return span + p + input + endP + endSpan>
</#function>

<#function underlineStyle input underlineLen style>
    <#assign span="<span style='display:  inline-block;border-bottom: .8px solid black;position: relative;bottom: 1px;width:"+(underlineLen?length*5.4)?string.number+"px;"+style+"'>">
    <#assign p="<p style='position:relative;top: 1.5px;'>">
    <#assign endP="</p>">
    <#assign endSpan="</span>">
    <#return span + p + input + endP + endSpan>
</#function>

<#function underlineSpan input underlineLen style>
    <#assign span="<span style='display:  inline-block;border-bottom: .8px solid black;position: relative;bottom: 0px;text-align:left;width:"+(underlineLen?length*5.4)?string.number+"px;"+style+"'>">
    <#assign span2="<span style='position:relative;top: 0px;'>">
    <#assign endSpan2="</span>">
    <#assign endSpan="</span>">
    <#return span + span2 + input + endSpan2 + endSpan>
</#function>

<#function underlineSpan2 input underlineLen style>
    <#assign span="<span style='display:  inline-block;border-bottom: .8px solid black;position: relative;bottom: 0px;text-align:left;width:"+(underlineLen?length*5.4)?string.number+"px;"+style+"'>">
    <#assign span2="<span style='position:relative;top: -3px;'>">
    <#assign endSpan2="</span>">
    <#assign endSpan="</span>">
    <#return span + span2 + input + endSpan2 + endSpan>
</#function>

<#function inc var1 var2>
    <#assign array = ['inc10', 'inc01', 'inc1'] />
    <#if array?seq_contains(var2)>
        <#assign incNum = var2?eval!0>
        <#return (var1?replace(',','')?number + incNum?number)?string('#.#')?replace('.0','')>
    <#else>
        <#return 'not support parameter'>
    </#if>
</#function>

<#function spanStyle style item >
    <#return '<span style = "'+style+'">'+item+'</span>'>
</#function>

<#function testSpace input space>
    <#assign resultString = '' />
    <#list 0..input?length-1 as i>
        <#if input?string[i] == '-'>
            <#return ''>
        </#if>
        <#if i == 0>
            <#assign resultString = resultString + input?string[i] >
        <#else >
            <#assign resultString = resultString + '<span style = "margin-left: '+space+'px;">'+input?string[i]+'</span>'>
        </#if>
    </#list>
    <#return resultString>
</#function>