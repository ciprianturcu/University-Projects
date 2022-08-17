bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 16
    b dw 1306
    c dd 70000
    d dq 1000

; our code starts here
segment code use32 class=code
    start:
        ;arithmetic expression to be calculated:(a+b+c)-(d+d)+(b+c)
        
        mov eax, dword [d]
        mov edx, dword [d+4]
        add eax, dword [d]
        adc edx, dword [d+4]
        ;calculates d+d in edx:eax 
        
        mov ebx,eax
        mov ecx,edx ;puts d+d in ecx:ebx
        
        mov ax,[c]
        mov dx,[c+2] ;dx:ax=c
        push dx
        push ax
        pop eax ;puts c in eax
        mov edx,0
           
        add ax, word [b] ;adds b to qword c in edx:eax
        
        add al, byte[a] ;adds a to b+c in edx:eax
        
        sub eax,ebx
        sbb edx,ecx
        ;calculates (a+b+c)-(d+d)
        
        add ax, word [b]
        add eax, dword [c]
        ;calculates (a+b+c)-(d+d)+(b+c)
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
