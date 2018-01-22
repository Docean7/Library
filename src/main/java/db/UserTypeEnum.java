package db;

public enum UserTypeEnum {

    REGISTERED_USER(0), UNREGISTERED_USER(1), LIBRARIAN(2), ADMIN(3), BANNED_USER(4);

    int type;

    UserTypeEnum(int type){
        this.type = type;
    }

    public int value(){
        return type;
    }

}
