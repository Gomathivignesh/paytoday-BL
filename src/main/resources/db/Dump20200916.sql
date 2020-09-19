
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
INSERT INTO `admin_user` VALUES (1,'GV','8056821325','gomathivigneshm@gmail.com','password',NULL,NULL),(2,'maha','8056821325','maha@gmail.com','0qhyC4P2eXn7QLrjP7aZ7A==',NULL,NULL);

CREATE TABLE `aeps_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aeps_id` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `message` varchar(45) DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4;

INSERT INTO `aeps_details` VALUES (40,'BC568360909','Pending',NULL,NULL,NULL),(41,'BC568360909','Pending',NULL,NULL,NULL),(42,'BC655466903','Pending',NULL,NULL,NULL);

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `hibernate_sequence` VALUES (43),(43),(43);


CREATE TABLE `retailer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `allow_dmt` int(11) DEFAULT '0',
  `allow_recharge` int(11) DEFAULT '0',
  `allow_pan` int(11) DEFAULT '0',
  `allow_aeps` int(11) DEFAULT '0',
  `allow_bbps` int(11) DEFAULT '0',
  `shopname` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `address1` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `isAllowDMT` tinyint(1) DEFAULT '0',
  `agent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;
INSERT INTO `retailer` VALUES (-1,'admin','admin@paytoday.in','sW9inPVm9Yua6waHFyUE1bioEmSmgS4BjOUCmi0BZAc=','2',0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'9080602406','2','3',NULL,NULL,0,-1),(6,'retailer1','retailer1@paytoday.in','il+q1yuLcNP8yDjR9p+Wf7Y8+i97gkBX8mcm+5LNrMdAWmHwk0X/YYiDjf4yEDFj','2',1,1,0,1,1,'GV-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','1',NULL,NULL,0,11),(11,'distributor','distributor@paytoday.in','ogBV4Vwakqq2a5+NaaV324sTuJaepaTmkdIiNsAbkqYsJYl6lu3G2wplMNnrezx2','2',1,1,0,1,1,'GV-distributor-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','2',NULL,NULL,0,-1),(16,'retailer2','retailer2@paytoday.in','il+q1yuLcNP8yDjR9p+WfwHlBrFpPPbfS+ExDtnWL0ZAWmHwk0X/YYiDjf4yEDFj','2',1,1,0,1,1,'GV-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','1',NULL,NULL,0,11),(17,'retailer3','retailer3@paytoday.in','il+q1yuLcNP8yDjR9p+Wf1zDPfFRARcG/QUZRu4zKAxAWmHwk0X/YYiDjf4yEDFj','1',0,0,0,0,0,'GV-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','1',NULL,NULL,0,11),(18,'retailer4','retailer4@paytoday.in','il+q1yuLcNP8yDjR9p+Wf5Q2kQ28PDB8gbIgpFr2crdAWmHwk0X/YYiDjf4yEDFj','1',0,0,0,0,0,'GV-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','1',NULL,NULL,0,11),(19,'retailer5','retailer5@paytoday.in','il+q1yuLcNP8yDjR9p+Wfw7b4WXF0ObxWmMPH3K5u9dAWmHwk0X/YYiDjf4yEDFj','1',0,0,0,0,0,'GV-stores','Eral','Tuticorin','Tuticorin','tutucorin','TN','India','8056821325','1','1',NULL,NULL,0,11),(28,'retailer6','retailer6@paytoday.in','il+q1yuLcNP8yDjR9p+Wf54DUkZUKTm8I2wx81UdKHVAWmHwk0X/YYiDjf4yEDFj','1',0,0,0,0,0,'shopgv','tuticorin','tuticorin','tuticorin','tuticorin','TN','IND','8056821325','1','1',NULL,NULL,0,11),(29,'test','test@gmail.com','4iUU5lsPimgUOs5dtE6m5LW1EbeXnYWvUW7Q3hAuWpw=','1',0,0,0,0,0,'gvtest','eral','eral','Tuticorin','tuticorin','TN','IND','8056821325','1','1',NULL,NULL,0,11),(35,'retailer8','retailer8','C0bTCVJroTOAdMKWVr1b3w==','1',0,0,0,0,0,'test','test','test','sds','sds','sd','sd','8056821325','1','1',NULL,NULL,0,11),(36,'distributor5','distributor5@gmail.com','ogBV4Vwakqq2a5+NaaV32yknnHNzDbf61ELeVyTsWHm1tRG3l52Fr1Fu0N4QLlqc','2',1,1,0,1,1,'as','as','as','as','s','as','as','8056821325','1','2',NULL,NULL,0,-1),(38,'retailer8','retailer8@paytoday.in','A0aIYAVWlXXeV0r9RdIuHWZpVCL9KfkmL8v/WHRndcw=','1',0,0,0,0,0,'dsasd','sdsf','asd','sdfsf','asd','sdfs','asd','08056821325','1','1',NULL,NULL,0,11);

CREATE TABLE `wallet` (
  `id` bigint(20) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `amount` decimal(19,2) NOT NULL,
  `img_url` varchar(255) NOT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `transaction_type` int(11) DEFAULT NULL,
  `transfer_type` varchar(25) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `approver_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ref_idx` (`user_id`,`approver_id`),
  KEY `user_ref1_idx` (`approver_id`),
  CONSTRAINT `user_ref` FOREIGN KEY (`user_id`) REFERENCES `retailer` (`id`),
  CONSTRAINT `user_ref1` FOREIGN KEY (`approver_id`) REFERENCES `retailer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
INSERT INTO `wallet` VALUES (32,NULL,NULL,5000.00,'D:\\paytoday\\retailer2@paytoday.in\\20200617914127743-970x646.jpg','PAYreta061791412',2,1,'Credit',16,11),(33,NULL,NULL,5000.00,'D:\\paytoday\\retailer2@paytoday.in\\20200617914162043-970x646.jpg','PAYreta061791416',1,1,'Credit',16,11),(34,NULL,NULL,2000.00,'D:\\paytoday\\distributor@paytoday.in\\20200617914194563-970x646.jpg','PAYdist061791419',2,1,'Credit',11,-1),(37,NULL,NULL,2000.00,'D:\\paytoday\\distributor5@gmail.com\\20200617915219743-970x646.jpg','PAYdist061791521',1,1,'Credit',36,-1),(39,NULL,NULL,2200.00,'D:\\paytoday\\distributor@paytoday.in\\20200618222502353-970x646.jpg','PAYdist061822250',2,1,'Credit',11,-1);
