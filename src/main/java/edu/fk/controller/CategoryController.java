package edu.fk.controller;

import java.util.List;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fk.entity.Result;
import edu.fk.pojo.Category;
import edu.fk.service.CategoryService;

//标注为一个控制器
@RestController
//路径映射
@RequestMapping("/category")
public class CategoryController {
    //自动注入依赖的服务处对象
    @Autowired
    CategoryService categoryService;

    //路径映射
    @RequestMapping("/queryAll")
    public Result queryAll() {
        List<Category> categories = categoryService.queryAll();
        Result res = new Result(true,"",categories);
        return res;
    }

    @RequestMapping("/queryByCondition")
    public PageResult<Category> queryByCondition(@RequestBody QueryPageBean pageBean){
        return  categoryService.queryByCondition(pageBean);
    }
    @RequestMapping("/delete")
    public Result deleteSeller(@RequestParam Integer row){
        int sign=categoryService.delete(row);
        if(sign>0){
            return new Result(true,"");
        }else{
            return new Result(true,"删除失败");
        }
    }
    @RequestMapping("/update")
    public Result updateSeller(@RequestBody Category category){
        int sign=categoryService.update(category);
        if(sign>0){
            return new Result(true,"修改成功");
        }else{
            return new Result(false,"修改失败");
        }
    }
    @RequestMapping("/add")
    public Result addSeller(@RequestBody Category category){
        int sign=categoryService.add(category);
        if(sign>0){
            return new Result(true,"添加成功");
        }else{
            return new Result(false,"添加失败");
        }
    }
}