bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 20    
    b db 40
    c db 150
    g dw 1000

; our code starts here
segment code use32 class=code
    start:
        ;arithmetic expression to be calculated: [(a+b+c)*2]*3/g
        mov ah,0
        mov bh,0
        mov ch,0
        mov al,[a]
        mov bl,[b]
        mov cl,[c]
        add ax,bx
        add ax,cx
        ;calculates (a+b+c) and stores result in ax
        
        mov bx,2
        mul bx
        ; calcultes the (a+b+c)*2 part in dx:ax
        mov bx,3
        mul bx
        ; calcultes the [(a+b+c)*2]*3 part in dx:ax
        
        div word [g]
        ; calculates the [(a+b+c)*2]*3/g part in ax
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
