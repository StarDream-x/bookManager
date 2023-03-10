package edu.whu.demo.controller;

import edu.whu.demo.entity.BookItem;
import edu.whu.demo.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author jiaxy
 * 图书控制器类。 负责提供API。可以在这个类中做请求响应数据的转换、验证，但不要写具体业务逻辑。
 */
@Api(description = "图书管理器")
@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    BookService bookService;

    // get: localhost:8088/books/1
    @ApiOperation("根据Id查询图书")
    @GetMapping("/{id}")
    public ResponseEntity<BookItem> getbook(@ApiParam("图书Id")@PathVariable long id){
        BookItem result = bookService.getBook(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    // get: localhost:8088/books
    // get: localhost:8088/books?name=作业
    // get: localhost:8088/books?name=作业&&complete=true
    @ApiOperation("根据条件查询图书")
    @GetMapping("")
    public ResponseEntity<Page<BookItem>> findbooks(@ApiParam("书籍名称")String name, @ApiParam("出版日期(起)")Date startTime,
                                                    @ApiParam("出版日期(止)")Date endTime, @ApiParam("作者")String author,
                                                    @ApiParam("出版商")String publisher, @ApiParam("评分(下限)")Double ratingLow,
                                                    @ApiParam("评分(上限)")Double ratingHigh, @ApiParam("书籍图片")String imgUrl,
                                                    @ApiParam("当前页号")Integer pNum, @ApiParam("页面大小")Integer pSize){
        Page<BookItem> result = bookService.findBooks(name, startTime, endTime, author, publisher, ratingLow, ratingHigh, imgUrl, pNum, pSize);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加图书")
    @PostMapping("")
    public ResponseEntity<String> addbook(@RequestBody BookItem book){
        try {
            BookItem result = bookService.addBook(book);
            return ResponseEntity.ok(""+result.getId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ApiOperation("修改图书")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatebook(@PathVariable long id,@RequestBody BookItem book){
        bookService.updateBook(id,book);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除图书")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletebook(@PathVariable long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("上传csv")
    @GetMapping("/upload")
    public ResponseEntity<String> addbook(@ApiParam("csv内容")String csvContent){
        String content = csvContent;
        return ResponseEntity.ok().build();
    }

}
