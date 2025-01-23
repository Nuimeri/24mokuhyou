# 見出し1
## 見出し2


```mermaid
erDiagram
    account {
        int id PK
        varchar mail UNIQUE
        varchar password
        date birthday
        datetime create_at
        int create_by
        datetime update_at
        int update_by
        tinyint is_deleted
    }
    anchored_post {
        int account_id FK
        char post_code FK
    }
    dm {
        int id PK
        varchar message
        int to_account_id FK
        int create_by FK
        date create_at
    }
    follow_relationship {
        int id PK
        int followey FK
        int follower FK
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    linked_site_sample {
        int id PK
        varchar name
        varchar icon
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    linked_site {
        int id PK
        int account_id FK
        varchar name
        varchar url
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    member {
        int id PK
        int team_id FK
        int account_id FK
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
        tinyint is_deleted
    }
    position_holder {
        int id PK
        int team_id FK
        int account_id FK
        int position_id FK
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
        tinyint is_deleted
    }
    position_name_sample {
        int id PK
        varchar position_name
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    position {
        int id PK
        int team_id FK
        varchar name
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
        tinyint is_deleted
    }
    post {
        int id PK
        char code UNIQUE
        int account_id FK
        varchar post_text
        varchar image
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
        tinyint is_deleted
    }
    profile {
        int id PK
        varchar username UNIQUE
        varchar nickname
        text description
        varchar icon
        date birthday
        int account_id FK
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    referral_user {
        int id PK
        int referrer FK
        int referred FK
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    team {
        int id PK
        varchar code UNIQUE
        varchar name
        varchar category
        int founder FK
        int leader FK
        varchar icon
        varchar music
        text description
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
        tinyint is_deleted
    }
    team_attribute_sample {
        int id PK
        varchar name
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }
    invitation {
        int id PK
        int team_id FK
        int sender FK
        int recipient FK
        int status
        datetime create_at
        int create_by FK
        datetime update_at
        int update_by FK
    }

    %% Relationships
    account ||--o{ anchored_post : "has"
    account ||--o{ dm : "sends"
    account ||--o{ follow_relationship : "follows"
    account ||--o{ linked_site_sample : "created"
    account ||--o{ linked_site : "owns"
    account ||--o{ member : "is part of"
    account ||--o{ position_holder : "holds"
    account ||--o{ position_name_sample : "created"
    account ||--o{ post : "creates"
    account ||--o{ profile : "has"
    account ||--o{ referral_user : "refers"
    account ||--o{ team_attribute_sample : "created"
    account ||--o{ team_invitation_status : "sends"
    team ||--o{ member : "has"
    team ||--o{ position : "has"
    team ||--o{ position_holder : "assigns"
    team ||--o{ team_invitation_status : "sends"
    position ||--o{ position_holder : "is held by"
    post ||--o{ anchored_post : "anchors"

```