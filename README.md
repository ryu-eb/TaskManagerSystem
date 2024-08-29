# タスク管理システム
## 概要
　業務タスクを管理するためのシステムです。実行待ちや実行中に加えて、精査待ちや精査中などのステータス表記があり、タスクによって変更できます。また、下位権限を持つユーザーの作成が行えるので、下位者のタスクの実行状況や履歴が見られます。
## 機能一覧
* ログイン機能
* ユーザー管理
* タスク管理
* 履歴管理

## 機能説明
### ログイン機能
* **ログイン**
    ![ログイン画面](src/main/resources/static/img/loginPage.pn)

* **ユーザー作成**
    ![ユーザー作成画面](src/main/resources/static/img/userRegister.pn)
  - このユーザー作成ページではROOT権限を持つユーザーの作成のみ可能。

### ユーザー管理
* **ユーザー一覧**
  ![ユーザー一覧](src/main/resources/static/img/userIndex.pn)
  - ROOT権限を持ったユーザーごとにグループが分かれていて、同じROOTユーザー配下のユーザーを確認できる。
  - ROOTユーザー作成時に、削除されたユーザーの参照用として「VOID_*」というユーザーも登録される。

* **ユーザー詳細**
  ![ユーザー詳細](src/main/resources/static/img/userDetail.pn)
  - ユーザー詳細画面では、基本的な情報と、現在担当しているタスク数が表示される。

* **ユーザー作成と削除**
  ![ユーザー削除](src/main/resources/static/img/userDelete.pn)
  - ユーザーの追加および削除はADMIN権限以上のみ。
  - ROOT > ADMIN > HIGH > LOW のいづれかの権限を付与したユーザーの作成が出来る。

### タスク管理
  - 実行待ち、実行中、精査待ち、精査中、完了 の5つのステータスがある。
### 履歴管理
  - 完了したタスクを確認出来る。
 
## MySQLセットアップ
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
