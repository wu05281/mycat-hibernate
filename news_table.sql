CREATE TABLE `news_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS MYCAT_SEQUENCE;  
CREATE TABLE `mycat_sequence` (
  `NAME` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '100',
  `max_value` int(11) NOT NULL,
  `min_value` int(11) NOT NULL,
  PRIMARY KEY (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Function structure for `mycat_seq_currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `mycat_seq_currval`(seq_name VARCHAR(50)) RETURNS varchar(64) CHARSET latin1
    DETERMINISTIC
BEGIN  
        DECLARE retval VARCHAR(64);
        SET retval="-999999999,null";  
        SELECT concat(CAST(current_value AS CHAR),",",CAST(increment AS CHAR) ) INTO retval   FROM MYCAT_SEQUENCE  WHERE name = seq_name;  
        RETURN retval ; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `mycat_seq_nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `mycat_seq_nextval`(seq_name VARCHAR(50)) RETURNS varchar(20) 
    DETERMINISTIC
BEGIN 
         UPDATE MYCAT_SEQUENCE  
                 SET current_value =  IF ((current_value + increment) > max_value , min_value , (current_value + increment))  WHERE name = seq_name; 
         RETURN mycat_seq_currval(seq_name) ;  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `mycat_seq_setval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_setval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `mycat_seq_setval`(seq_name VARCHAR(50), value INTEGER) RETURNS varchar(64) CHARSET latin1
    DETERMINISTIC
BEGIN  
         UPDATE MYCAT_SEQUENCE  
                   SET current_value = value  
                   WHERE name = seq_name;  
         RETURN mycat_seq_currval(seq_name);  
END
;;
DELIMITER ;

INSERT INTO MYCAT_SEQUENCE VALUES ('NEWS_TABLE', 10000, 30，99999，10000);
