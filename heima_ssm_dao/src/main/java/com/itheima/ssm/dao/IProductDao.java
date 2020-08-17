package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IProductDao {
    @Select("SELECT * FROM product")
    List<Product> findAll()throws Exception;

    @Insert("insert into product(id,productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(uuid(),#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product)throws Exception;

    //根据id查询产品的操作
    @Select("select* from product where id=#{id}")
    public Product findById(String id)throws Exception;
}
