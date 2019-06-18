package com.mybatis.enums;

/**
 * <br>
 *  sql枚举 定义好 自己的baseDao默认方法
 * @author 永健
 * @since 2019/4/23 15:59
 */
public enum  SuperSqlMethod
{
    /**
     * <br>
     *  插入一条数据
     */
    INSERT_ONE("insert","插入一条数据","<script>\nINSERT INTO %s %s VALUES %s\n</script>"),
    SELECT_BY_ID("selectById","根据主键获取","<script>\nSELECT %s from %s where %s = #{%s}\n</script>"),
    DELETE_BY_ID("deleteById","根据主键杀出","<script>\ndelete from %s where %s = #{%s}\n</script>"),
    SELECT_LIST("selectList", "查询满足条件所有数据", "<script>\nSELECT %s FROM %s %s\n</script>"),
    UPDATE_BY_ID("updateById", "根据ID 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>");

    private final String method;
    private final String desc;
    private final String sql;

    SuperSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
