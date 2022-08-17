bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 300
    b db 55
    c dd 70000
    x dq 10000

; our code starts here
segment code use32 class=code
    start:
        ;arithmetic expression to be calculated: (a*a+b+x)/(b+b)+c*c
        
        mov ax, word [a]
        mov bx, word [a]
        ;calculates a*a
        imul bx
        push dx
        push ax
        pop eax
        cdq
        ;converts a*a from word to quadword necessary for further calculations 
        
        mov ebx,eax
        mov ecx,edx 
        ;moving a*a in ecx:ebx
        
        mov al,byte [b]
        cbw
        cwde
        cdq
        ;converts b from byte to quadword
        
        add ebx,eax
        adc ecx,edx
        ;calculates a*a+b 
        
        add ebx, dword [x]
        adc ecx, dword [x+4]
        ;calculates a*a+b+x

        mov al,byte [b]
        add al,byte [b]
        ;calculates b+b 
        cbw
        cwde
        ;converts b+b from byte to doubleword in eax
        mov edx,eax
        mov eax,ebx
        mov ebx,edx
        mov edx,ecx
        idiv ebx
        mov ebx,eax
        ;calculates (a*a+b+x)/(b+b) in ebx
        
        
        mov eax, dword [c]
        imul dword [c]
        ;calculates c*c in edx:eax
        add eax,ebx
        ;calculates (a*a+b+x)/(b+b)+c*c
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
