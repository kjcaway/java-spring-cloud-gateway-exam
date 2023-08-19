CREATE TABLE `tbl_route_info`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `code`       VARCHAR(45)  NOT NULL,
    `proxy_host` VARCHAR(255) NOT NULL,
    `is_auth`    TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
