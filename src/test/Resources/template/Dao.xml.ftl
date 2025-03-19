<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.dao.${className}DAO">
    <!-- 可根据自己的需求，是否要使用 -->
    <resultMap type="${package}.entity.${className}Entity" id="${classname}Map">
    <#list columns as column>
        <result property="${column.attrname}" column="${column.columnName}"/>
    </#list>
    </resultMap>

    <select id="querySQL" resultType="${package}.entity.${className}Entity">
        ${r'${SqlSelect}'}
    </select>

    <select id="get" resultType="${package}.entity.${className}Entity">
        SELECT * FROM ${tableName} WHERE ${pk.columnName} = ${r'#{id}'}
    </select>

    <select id="findList" resultType="${package}.entity.${className}Entity">
        SELECT * FROM ${tableName} ORDER BY ${pk.columnName} DESC
        <if test="offset != null and limit != null">
            LIMIT ${r'#{offset}'}, ${r'#{limit}'}
        </if>
    </select>

    <select id="findCount" resultType="int">
        SELECT COUNT(*) FROM ${tableName}
    </select>

    <insert id="save" parameterType="${package}.entity.${className}Entity"<#if pk.extra == 'auto_increment'> useGeneratedKeys="true" keyProperty="${pk.attrname}"</#if>>
        INSERT INTO ${tableName} (
    <#list columns as column>
        <#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>
            `${column.columnName}`<#if column?has_next>, </#if>
        </#if>
    </#list>
        ) VALUES (
<#list columns as column>
    <#if column.attrname == 'creatdate'>
        NOW()<#if column?has_next>, </#if>
    <#else>
        <#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>
            ${r'#{'}${column.attrname}${r'}'}<#if column?has_next>, </#if>
        </#if>
    </#if>
</#list>
        )
    </insert>

    <update id="update" parameterType="${package}.entity.${className}Entity">
        UPDATE ${tableName}
        <set>
    <#list columns as column>
        <#if column.columnName != pk.columnName>
            <if test="${column.attrname} != null">`${column.columnName}`=${r'#{'}${column.attrname}${r'}'}<#if column?has_next>,</#if></if>
        </#if>
    </#list>
        </set>
        WHERE ${pk.columnName} = ${r'#{'}${pk.attrname}${r'}'}
    </update>

    <delete id="delete">
        DELETE FROM ${tableName} WHERE ${pk.columnName} = ${r'#{value}'}
    </delete>

    <delete id="deleteBatch">
        DELETE FROM ${tableName} WHERE ${pk.columnName} IN
        <foreach item="${pk.attrname}" collection="array" open="(" separator="," close=")">
            ${r'#{'}${pk.attrname}${r'}'}
        </foreach>
    </delete>
</mapper>