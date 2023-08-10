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



