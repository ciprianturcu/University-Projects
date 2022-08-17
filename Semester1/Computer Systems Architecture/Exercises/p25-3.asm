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
        mul bx  
        push dx
        push ax
        pop eax
        ;calculates a*a in as dword in eax
        
        mov bl, byte [b]
        mov bh,0
        mov cx,0
        push cx
        push bx 
        pop ebx
        ;converts b from byte to doubleword
        add eax,ebx
        ;calculates a*a+b
        mov edx,0
        ;converts a*a+b from doubleword to quadword in edx:eax
        
        add eax, dword [x]
        adc edx, dword [x+4]
        ;calculates a*a+b+x in edx:eax
        
        mov bl,[b]
        add bl,byte [b]
        ;calculates b+b
        mov bh,0
        mov cx,0
        push cx
        push bx
        pop ebx
        ;converts b+b from byte to doubleword
        
        div ebx
        mov ebx,eax
        mov ecx,0
        ;calculates (a*a+b+x)/(b+b)
        
        mov eax, dword [c]
        mul dword [c]
        ;calculates c*c
        add eax, ebx
        ;calculates (a*a+b+x)/(b+b)+(c*c)
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
