# タスク管理システム
## 概要
　業務タスクを管理するためのシステムです。実行待ちや実行中に加えて、精査待ちや精査中などのステータス表記があり、タスクによって変更できます。また、下位権限を持つユーザーの作成が行えるので、下位者のタスクの実行状況や履歴が見られます。

## 機能一覧
 - ログイン機能
 - ユーザー管理
 - タスク管理
 - 履歴管理
<br><br>
## 機能説明
### ログイン機能
   - ログイン画面
 ![ログイン画面](src/main/resources/static/img/loginpage.png)
### ユーザー管理
### タスク管理
### 履歴管理
 
## DB作成
```sql
CREATE DATABASE taskmanager;
USE taskmanager;

CREATE table user (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
username VARCHAR(255) NOT NULL, 
password VARCHAR(255) NOT NULL, 
parentId INT NOT NULL, 
authId INT NOT NULL, 
);

CREATE table tasks (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
title VARCHAR(50), 
description VARCHAR(500), 
due DATE,
dblCheck BOOLEAN,
createrId INT NOT NULL,
authRangeId INT NOT NULL,
);

CREATE table tasks (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
title VARCHAR(50), 
description VARCHAR(500), 
createrId INT NOT NULL,
parentId INT NOT NULL,
authRangeId INT NOT NULL,
);

CREATE table history (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
done DATE,
doneUserId INT, 
dbl DATE,
dblUserId INT, 
taskId INT NOT NULL, 
statusId INT NOT NULL, 
);

CREATE table authorities (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(10)
);

CREATE table status (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(10)
param VARCHAR(10)
);

INSERT INTO authorities ( name ) VALUES ( 'OWN' );
INSERT INTO authorities ( name ) VALUES ( 'ROOT' );
INSERT INTO authorities ( name ) VALUES ( 'ADMIN' );
INSERT INTO authorities ( name ) VALUES ( 'HIGH' );
INSERT INTO authorities ( name ) VALUES ( 'LOW' );

INSERT INTO status ( name, param ) VALUES ('実行待ち', 'inactive');
INSERT INTO status ( name, param ) VALUES ( '実行中', 'active' );
INSERT INTO status ( name, param ) VALUES ( '精査待ち', 'standby' );
INSERT INTO status ( name, param ) VALUES ( '精査中', 'check' );
INSERT INTO status ( name, param ) VALUES ( '完了', 'complete' );
```
