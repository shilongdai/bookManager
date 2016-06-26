USE bookManager;
CREATE TABLE UserPrincipal(Id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, Username VARCHAR(64), Password VARCHAR(1000), AccountNonExpired BOOLEAN, AccountNonLocked BOOLEAN, CredentialsNonExpired BOOLEAN, Enabled BOOLEAN);
CREATE TABLE UserPrincipal_Authority(UserId BIGINT NOT NULL, Authority VARCHAR(100) NOT NULL, UNIQUE KEY UserPrincipal_Authority_User_Authority (UserId, Authority), CONSTRAINT UserPrincipal_Authority_UserID FOREIGN KEY (UserId) REFERENCES UserPrincipal(Id) ON DELETE CASCADE );
CREATE TABLE Book(Id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, Author VARCHAR(50) CHARACTER SET utf8, Title VARCHAR(100) CHARACTER SET utf8, Available BOOLEAN, Published DATE, Genre VARCHAR(20), Lang VARCHAR(40) CHARACTER SET utf8, Description VARCHAR(10000) CHARACTER SET utf8, Isbn VARCHAR(13) CHARACTER SET utf8, Location VARCHAR(50) CHARACTER SET utf8, UserId BIGINT NOT NULL, CONSTRAINT Book_Owner FOREIGN KEY (UserId) REFERENCES UserPrincipal(Id) ON DELETE CASCADE);