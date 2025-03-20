package com.example.testgiuaky2.mapper;
import com.example.testgiuaky2.model.Category;
import com.example.testgiuaky2.model.CategoryResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Chuyển từ Category -> CategoryResponse
    public static CategoryResponse toCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(
                category.getName(),
                category.getDescription(),
                category.getImages() // imageUrl trong CategoryResponse tương ứng với images trong Category
        );
    }

    // Chuyển từ CategoryResponse -> Category
    public static Category toCategory(CategoryResponse categoryResponse, int id) {
        if (categoryResponse == null) {
            return null;
        }
        return new Category(
                id,  // Cần truyền ID vào khi tạo mới
                categoryResponse.getName(),
                categoryResponse.getImageUrl(), // imageUrl trong CategoryResponse tương ứng với images trong Category
                categoryResponse.getDescription()
        );
    }

    public static List<Category> toCategoryList(List<CategoryResponse> categoryResponses) {
        if (categoryResponses == null || categoryResponses.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu đầu vào null hoặc rỗng
        }
        return categoryResponses.stream()
                .map(response -> toCategory(response, 0)) // ID mặc định là 0, cần cập nhật nếu có ID thật
                .collect(Collectors.toList());
    }
}