package ${package}.entity;

import ${package}.base.BaseEntity;
<#if hasDate>
    import java.util.Date;
</#if>
<#if hasBigDecimal>
    import java.math.BigDecimal;
</#if>
import lombok.Data;

/**
* @AUTO ${comments!"No comments"}
* @FILE ${className}Entity.java
* @DATE ${datetime!"Unknown date"}
* @Author ${author!"Unknown author"}
*/
@Data
public class ${className}Entity extends BaseEntity<${className}Entity> {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    <#if column.attrname != 'id' && column.attrname != 'creatdate'>
    /** ${column.comments!"No comments"} */
    private ${column.attrType!"String"} ${column.attrname};
    </#if>
</#list>
}