package com.inchcape.inchcapeisthebest.input;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ActorInput {
    @NonNull
    @Size(min = 1, max = 45)
    private String firstname;

    @NonNull
    @Size(min = 1, max = 45)
    private String lastName;
}
