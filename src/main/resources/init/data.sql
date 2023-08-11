INSERT INTO `order_basket`
    (`cart_id`, `product_id`, `iuser`, `count`, `created_at`)
VALUES
	(7, 23, 2, 3, '2023-08-10 17:22:00'),
	(8, 364, 1, 4, '2023-08-10 17:22:16');

insert into `review`
	(`review_id`, `iuser`, `product_id`, `ctnt`)
VALUES
    (1, 1, 1, '이것은 테스트용 리뷰이다');

insert into `review`
(`review_id`, `iuser`, `product_id`, `ctnt`)
VALUES
	(1, 1, 1, '이것은 테스트용 리뷰이다');