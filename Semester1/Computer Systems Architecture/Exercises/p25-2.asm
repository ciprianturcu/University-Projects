bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 17
    b dw 300
    c dd 70000
    d dq 5000

; our code starts here
segment code use32 class=code
    start:
        ;arithmetic expression to be calculated:(a + b - c) + (a + b + d) - (a + b)
        mov ebx, dword [d]
        mov ecx, dword [d+4]
        
        mov al,[a]
        cbw
        add ax, word [b]
        cwde
        cdq
        add ebx,eax
        adc ecx,edx
        ;calculates a+b+d in ecx:ebx
        
        mov al,[a]
        cbw
        add ax, word [b]
        cwde
        sub eax, dword[c]
        mov edx,0
        add ebx,eax
        adc ecx,edx
        ;calculates (a+b-c)+(a+b+d)
        
        mov al,[a]
        cbw
        add ax,word[b]
        cwde
        cdq
        sub ebx,eax
        sbb ecx,edx
        ;calculates (a + b - c) + (a + b + d) - (a + b)
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
