package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Tag(name = "Category API's", description = "API's for managing categories")
    @Operation(summary = "Get All Categories", description = "API to get all categories")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "All categories are displayed"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getALlCategories(
            @RequestParam(name = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
            @RequestParam (name = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
            @RequestParam (name = "sortBy" ,defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false)  String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @Tag(name = "Category API's", description = "API's for managing categories")
    @Operation(summary = "Create Category", description = "API to create a category")
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(savedCategoryDTO, HttpStatus.CREATED) ;
    }

    @Tag(name = "Category API's", description = "API's for managing categories")
    @Operation(summary = "Delete Category", description = "API to delete category")
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){

            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);


    }
    @Tag(name = "Category API's", description = "API's for managing categories")
    @Operation(summary = "Update Category", description = "API to update category")
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
           CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
           return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK );

    }
}
