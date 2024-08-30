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
  - ログイン画面です。
    <br>![ログイン画面](https://github.com/user-attachments/assets/a972715f-5005-4afc-a3e2-e28ba493b0cb)<br>

* **ユーザー作成**
  - このユーザー作成ページでは「ROOT」権限を持つユーザーの作成のみ可能です。
    <br>![ユーザー作成画面](https://github.com/user-attachments/assets/616d9f4d-9ce6-4396-b190-a4ddbbb1e4da)<br>


### ユーザー管理
* **ユーザー一覧**
  - ROOT権限を持ったユーザーごとにグループが分かれていて、同じグループ配下のユーザーを確認できます。
  - ROOTユーザー作成時に、削除されたユーザーの参照用として「VOID_*」というユーザーも登録されます。タスクの作成者がこのユーザーである場合、ROOTユーザーのみ閲覧可能です。
    <br>![ユーザー一覧](https://github.com/user-attachments/assets/56d5fab1-91b5-4dfb-a604-53a1ddb071f6)<br>


* **ユーザー詳細**
  - ユーザー詳細画面では、基本的な情報と、現在担当しているタスク数が表示されます。
    <br>![ユーザー詳細](https://github.com/user-attachments/assets/2275bcc5-6bb8-4437-8d0e-2642cca8b95d)<br>

* **ユーザー作成と削除**
  - ユーザーの追加および削除はADMIN権限以上のみです。
  - 「ROOT > ADMIN > HIGH > LOW」のいづれかの権限を付与したユーザーの作成ができ、自分より下の権限を持つユーザーの作成が可能です。
  - この画面から作成したユーザーは自動的に同グループに入ります。
    <br>![ユーザー作成](https://github.com/user-attachments/assets/9b8e0cc7-290b-40b4-844d-1da83ded74ad)<br>
  - ユーザー削除時画面です。
    <br>![ユーザー削除](https://github.com/user-attachments/assets/ae1e0d93-7eb0-4ed5-b983-8f90f34a947c)<br>
  - ユーザー削除時は、タスクや履歴の参照先ユーザーが、削除されるユーザーから「VOID_*」ユーザーに変更されます。
    <br>![ユーザー削除時参照変更](https://github.com/user-attachments/assets/c2ae3e82-0a8a-4b31-9976-108960d8d5ca)<br>

### タスク管理
* **タスク一覧**
  - 実行待ち、実行中、精査待ち、精査中、完了 の5つのステータスがあり、画面には期限日が近い順で表示されます。完了のタスクは表示されません。
  - 同グループの自分以下の権限を持つタスクが表示されます。
    <br>![タスク一覧](https://github.com/user-attachments/assets/f7bed24d-97a0-4cc5-8f35-c14888e63906)<br>
  - 右上のステータスを表すボタンを押下することで、そのタスクのステータスを変更できます。また、実行中または精査中に変更したユーザーのみが精査待ちまたは完了に変更できます。
    <br>![タスクトグル](https://github.com/user-attachments/assets/318c7884-db24-4784-8a3f-c1a26210360b)<br>
    <br>![タスクトグル失敗](https://github.com/user-attachments/assets/d7c67db4-41ca-418a-b0aa-da08dcf55585)<br>
  - タスクとは別にテンプレートがあり、ここではその確認が出来ます。権限等はタスクと同様の設定です。
    <br>![テンプレート一覧](https://github.com/user-attachments/assets/9b0c78d5-0685-4e29-9d56-0b479ea1091c)<br>

* **詳細画面**
  - タスクのより詳細な情報が確認できます。またこの画面からのステータス変更も可能です。
  - タスクのステータスが実行中より後の状態なら作業者が表示され、精査中の場合は精査者が表示されます。
    <br>![タスク作業者](https://github.com/user-attachments/assets/8b8d57d3-2194-432a-9176-3b9078a47b10)<br>

* **新規作成と削除**
  - タスク新規作成画面です。新規作成時に同内容でテンプレートへ保存要否を選択できます。
  - タスクの権限には[OWN]があり、これは自分自身のみに表示されます。また、自分以下の権限を持つタスクを作成可能で、「LOW」ユーザーはOWN権限を持つタスクの作成のみが可能です。テンプレートについても同様です。
    <br>![タスク新規作成](https://github.com/user-attachments/assets/26f29ad8-219f-4afb-a46f-318ef2172fdc)<br>
    <br>![テンプレート新規作成](https://github.com/user-attachments/assets/e51371d3-0f77-4889-965f-5c1b3cc72366)<br>
  - タスクの新規作成時に、右上の「テンプレート」を押下することでテンプレートを選べます。
    <br>![テンプレート選択](https://github.com/user-attachments/assets/c4021cf7-a09a-42d9-868f-bd51ea5da23e)<br>
  - タスク削除画面です。タスク削除時にはそのタスクが持つ履歴も一緒に削除されます。
    <br>![タスク削除](https://github.com/user-attachments/assets/8f893444-a634-4e5f-a507-6de1d59f687d)<br>
  - テンプレートの削除は、テンプレート一覧画面から行えます。
    <br>![テンプレート削除](https://github.com/user-attachments/assets/6c7d2d0f-99c3-4a5b-bad1-3a7e1cb46bad)<br>

### 履歴管理
  - 完了したタスクを確認出来ます。
    <br>![履歴一覧](https://github.com/user-attachments/assets/62e40c39-3404-4904-af97-3800c878c03f)<br>
  - 履歴の詳細画面です。ここでは精査者や作業者、完了日などを確認出来ます。
    <br>![履歴詳細](https://github.com/user-attachments/assets/7dbebce3-a4f5-4c83-a32b-e8e62cdf2270)<br>
  - 履歴の削除画面です。
    <br>![履歴削除](https://github.com/user-attachments/assets/6dab7702-1322-4b9d-bf35-5c6d585b4fa2)<br>
 
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
## 使用技術
* **フロントエンド** HTML, CSS, JavaScript
* **バックエンド** Java, Spring Boot
* **データベース** MySQL
* **テンプレートエンジン** Thymeleaf
