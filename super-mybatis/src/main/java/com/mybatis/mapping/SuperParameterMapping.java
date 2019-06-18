package com.mybatis.mapping;

/**
 * <br>
 * #{id} 这个参数的数据信息
 * @author 永健
 * @since 2019/5/15 17:54
 */
public class SuperParameterMapping
{
   private String name;
   private JdbcType type;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public JdbcType getType()
   {
      return type;
   }

   public void setType(JdbcType type)
   {
      this.type = type;
   }
}
