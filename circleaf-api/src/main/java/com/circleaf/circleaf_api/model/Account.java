package com.circleaf.circleaf_api.model;

import java.time.LocalDate;
import java.util.Date;

import com.circleaf.circleaf_api.validator.annotation.Past13Years;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data   
public class Account {
    private Long id;

    @NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "有効なメールアドレスを入力してください")
    private String mail;
    
    @NotBlank(message = "パスワードを入力してください")
	@Size(min = 4, max = 20, message = "パスワードは4文字以上、20文字以下で入力してください")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
         message = "パスワードは大文字、小文字、数字を含む必要があります。特殊文字は含めないでください。")
    private String password;

    @NotNull(message = "生年月日を入力してください")
    @Past13Years
    private LocalDate birthday;

    private Date createAt;
    private Date updateAt;
    private Long createBy;
    private Long updateBy;
    private boolean isDeleted;
}
