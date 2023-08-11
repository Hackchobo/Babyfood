drop table IF exists `order_basket`;
CREATE TABLE IF NOT EXISTS `order_basket` (
    `cart_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `product_id` bigint(20) unsigned NOT NULL,
    `iuser` bigint(20) unsigned NOT NULL,
    `count` int(11) NOT NULL,
    `created_at` datetime DEFAULT current_timestamp(),
    PRIMARY KEY (`cart_id`) USING BTREE,
    KEY `product_id` (`product_id`) USING BTREE,
    KEY `iuser` (`iuser`) USING BTREE,
    CONSTRAINT `order_basket_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    CONSTRAINT `order_basket_ibfk_2` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`)
    );


drop table IF exists `review`;
CREATE TABLE `review` (
    `review_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `iuser` BIGINT(20) UNSIGNED NOT NULL,
    `product_id` BIGINT(20) UNSIGNED NOT NULL,
    `ctnt` TEXT NOT NULL ,
    PRIMARY KEY (`review_id`) USING BTREE,
    INDEX `iuser` (`iuser`) USING BTREE,
    INDEX `product_id` (`product_id`) USING BTREE,
    CONSTRAINT `review_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`),
    CONSTRAINT `review_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);
