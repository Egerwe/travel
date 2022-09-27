package edu.fk.controller;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fk.entity.Result;
import edu.fk.pojo.Seller;
import edu.fk.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @RequestMapping("/queryByCondition")
    public PageResult<Seller> queryByCondition(@RequestBody QueryPageBean pageBean){
        return  sellerService.queryByCondition(pageBean);
    }
    @RequestMapping("/delete")
    public Result deleteSeller(@RequestParam Integer row){
        int sign=sellerService.delete(row);
        if(sign>0){
            return new Result(true,"");
        }else{
            return new Result(true,"删除失败");
        }
    }
    @RequestMapping("/update")
    public Result updateSeller(@RequestBody Seller seller){
        int sign=sellerService.update(seller);
        if(sign>0){
            return new Result(true,"修改成功");
        }else{
            return new Result(false,"修改失败");
        }
    }
    @RequestMapping("/add")
    public Result addSeller(@RequestBody Seller seller){
        int sign=sellerService.add(seller);
        if(sign>0){
            return new Result(true,"添加成功");
        }else{
            return new Result(false,"添加失败");
        }
    }

}
