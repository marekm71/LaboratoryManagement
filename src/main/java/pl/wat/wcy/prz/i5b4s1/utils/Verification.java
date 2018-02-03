package pl.wat.wcy.prz.i5b4s1.utils;

import pl.wat.wcy.prz.i5b4s1.database.model.User;

import java.util.List;

public class Verification {
    public boolean existUserLogin(List<User> list, String loginInput){
        for(User c: list){
            if(loginInput.equals(c.getLogin())){
                return true;
            }
        }
        return false;
    }
    public boolean verificationLoginLength(String loginInput){
        return loginInput.length()>=5;
    }
    public boolean verificationPasswordLength(String passwordInput){
        return passwordInput.length()>=5;
    }
    public boolean verificationEmailLength(String emailInput){
        return emailInput.length()>=5;
    }
    public boolean verificationPasswords(String passwordInput, String password2Input){
        return passwordInput.equals(password2Input);
    }
    public boolean verificationOldPassword(User user, String oldPasswordInput){
        return user.getPassword().equals(oldPasswordInput);
    }
    private boolean newAndOldPassword(User user, String newPasswordInput){
        if(user.getPassword().equals(newPasswordInput)) return false;
        else return true;
    }



    public boolean verificationLog(User user, String passwordInput){
        if(user==null) return false;
        if(user.getPassword().equals(passwordInput)) return true;
        else return false;
    }
    public boolean verificationRegister(List<User> list, String loginInput, String passwordInput, String password2Input, String emailInput ) {
        if (!existUserLogin(list, loginInput) && verificationLoginLength(loginInput) && verificationPasswordLength(passwordInput) && verificationPasswords(passwordInput, password2Input) && verificationEmailLength(emailInput)) {
            return true;
        } else return false;
    }
    public boolean verificationChangePassword(User user, String oldPasswordInput, String newPasswordInput, String newRepeatPasswordInput){
        if(verificationOldPassword(user, oldPasswordInput) && verificationPasswords(newPasswordInput, newRepeatPasswordInput) && verificationPasswordLength(newPasswordInput) && newAndOldPassword(user,newPasswordInput)) {
            return true;
        }
        else return false;
    }
}
