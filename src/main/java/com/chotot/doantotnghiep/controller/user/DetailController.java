package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CommentDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.dto.UserDto;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.security.MyUser;
import com.chotot.doantotnghiep.service.impl.ICommentService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DetailController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;

    @GetMapping("/product/detail/{id}")
    public String getProductById(Model model, @PathVariable("id")Long id){
        ProductDto product = productService.findById(id);
        model.addAttribute("productdetail", product);

        List<CommentDto> comment = commentService.getCommentByProduct(id);
        model.addAttribute("comment", comment);


        CommentDto cmt = new CommentDto();
        model.addAttribute("ctt", cmt);


        return "detail";
    }
    @PostMapping("/comment")
    public String comment(@ModelAttribute("cmt") CommentDto commentDto,
                          @RequestParam("productId") Long id,
                          RedirectAttributes redirectAttributes){
        commentService.insert(commentDto, id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/product/detail/{id}";
    }
    @PostMapping("/reply")
    public String reply(@ModelAttribute("cmt") CommentDto commentDto,
                          @RequestParam("productId") Long id,
                          @RequestParam("cmtId") Long cmtId,
                          RedirectAttributes redirectAttributes){
        commentDto.setCmtId(cmtId);
        commentService.insert(commentDto, id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/product/detail/{id}";
    }

}
