package Server.Commands;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegistrationCommand();
        }
    },
    GETCATALOG {
        {
            this.command = new GetCatalogCommand();
        }
    },
    INDEXBOOK {
        {
            this.command = new IndexBookCommand();
        }
    },
    SEARCHBOOK {
        {
            this.command = new SearchBookCommand();
        }
    },
    ADDBOOKTOUSER {
        {
            this.command = new AddBookToUserCommand();
        }
    },
    BOOKDELIVERED {
        {
            this.command = new BookDeliveredCommand();
        }
    },
    BOOKRETURNED {
        {
            this.command = new BookReturnedCommand();
        }
    },
    ONEDAYORDER {
        {
            this.command = new OneDayOrderCommand();
        }
    }
    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
