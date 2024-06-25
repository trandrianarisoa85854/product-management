package io.ennov.productmanagement.controller;

import io.ennov.productmanagement.exception.ServiceException;
import io.ennov.productmanagement.model.domain.Product;
import io.ennov.productmanagement.model.dto.ApiErrorResponse;
import io.ennov.productmanagement.model.dto.ProductDto;
import io.ennov.productmanagement.service.product.ProductService;
import io.ennov.productmanagement.service.utils.ProductModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/products")
@Tag(name = "ProductController", description = "Api Product /api/v1/product")
public class ProductController {
    private final ProductService        productService;
    private final ProductModelAssembler productModelAssembler;

    @Operation(summary = "Get list of product")
    @ApiResponse(responseCode = "200", description = "Success operation", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Anauthorized access", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Access to this resource is denied", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/")
    public CollectionModel<EntityModel<Product>> all() {
        log.info("Product.getAll ...");

        List<EntityModel<Product>> employees = productService.retrieves().stream()
                .map(productModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @Operation(summary = "Get product for id")
    @ApiResponse(responseCode = "200", description = "Success operation", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Anauthorized access", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Access to this resource is denied", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/{id}")
    public EntityModel<Product> one(@NotNull(message = "The product's Id is required.")
                                    @Positive(message = "The product's Id must be greater than 0")
                                    @PathVariable("id") long id) {
        log.info("ProductController - Product.getById with id : {} ...", id);

        return productModelAssembler.toModel(productService.retrieve(id));
    }

    @Operation(summary = "Create product")
    @ApiResponse(responseCode = "201", description = "Success creation operation", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Anauthorized access", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Access to this resource is denied", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto, HttpServletRequest request) {
        log.info("ProductController - Product.create product ...");

        EntityModel<Product> entityModel = productModelAssembler.toModel(productService.create(productDto));
        if (Objects.nonNull(entityModel)) {
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } else {
            throw new ServiceException("Error in creating the Product resource.");
        }
    }

    @Operation(summary = "Update product for id")
    @ApiResponse(responseCode = "200", description = "Success operation", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Anauthorized access", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Access to this resource is denied", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody ProductDto productDto,
                                    @NotNull(message = "The product's Id is required.")
                                    @Positive(message = "The product's Id must be greater than 0")
                                    @PathVariable("id") Long id) {
        log.info("ProductController - Product.update with id : {} ...", id);
        if (Objects.isNull(id)) {
            throw new ServiceException("Product id param is madatory in update process");
        }

        EntityModel<Product> entityModel = productModelAssembler.toModel(productService.update(productDto, id));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Delete product for id")
    @ApiResponse(responseCode = "204", description = "Success delete operation", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Anauthorized access", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Access to this resource is denied", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@NotNull(message = "The product's Id is required.")
                                         @Positive(message = "The product's Id must be greater than 0")
                                         @PathVariable("id") Long id) {
        log.info("ProductController - Product.delete with id : {} ...", id);

        return this.productService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
