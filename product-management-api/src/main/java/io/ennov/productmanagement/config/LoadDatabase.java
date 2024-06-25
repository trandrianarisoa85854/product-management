package io.ennov.productmanagement.config;

import com.github.javafaker.Faker;
import io.ennov.productmanagement.model.domain.Product;
import io.ennov.productmanagement.model.domain.Role;
import io.ennov.productmanagement.model.dto.RegisterDto;
import io.ennov.productmanagement.repository.ProductRepository;
import io.ennov.productmanagement.repository.RoleRepository;
import io.ennov.productmanagement.repository.UserRepository;
import io.ennov.productmanagement.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
class LoadDatabase {
    private final RoleRepository    roleRepository;
    private final UserService       userService;
    private final UserRepository    userRepository;
    private final ProductRepository productRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            log.info("LoadDatabase -> Create Role : ROLE_ADMIN, ROLE_USER");
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
            roleRepository.save(Role.builder().name("ROLE_USER").build());

            log.info("LoadDatabase -> Create user : user1@gmail.com");
            userService.register(RegisterDto.builder()
                    .firstname("John")
                    .lastname("Doe")
                    .email("john.doe@gmail.com")
                    .password("123")
                    .role("ROLE_ADMIN")
                    .build());

            log.info("LoadDatabase -> Create user : user2@gmail.com");
            userService.register(RegisterDto.builder()
                    .firstname("Fabien")
                    .lastname("Cyrille")
                    .email("fabien.cyrille@gmail.com")
                    .password("123")
                    .role("ROLE_USER")
                    .build());

            log.info("LoadDatabase -> Create product for user");
            List<Product> productsJohnDoe = new ArrayList<>();
            List<Product> productsFabienCyrille = new ArrayList<>();

            var userJohnDoe = userRepository.findByEmail("john.doe@gmail.com").get();
            var userFabienCyrille = userRepository.findByEmail("fabien.cyrille@gmail.com").get();

            for (int i = 0; i < 5; i++) {
                Faker faker = new Faker();
                productsJohnDoe.add(Product.builder()
                        .title(faker.commerce().productName())
                        .price(Double.valueOf(StringUtils.replace(faker.commerce().price(), ",", ".")))
                        .description(faker.lorem().paragraph(3))
                        .user(userJohnDoe)
                        .build());

                faker = new Faker();
                productsFabienCyrille.add(Product.builder()
                        .title(faker.commerce().productName())
                        .price(Double.valueOf(StringUtils.replace(faker.commerce().price(), ",", ".")))
                        .description(faker.lorem().paragraph(4))
                        .user(userFabienCyrille)
                        .build());
            }

            productRepository.saveAll(productsJohnDoe);
            productRepository.saveAll(productsFabienCyrille);
        };
    }
}

