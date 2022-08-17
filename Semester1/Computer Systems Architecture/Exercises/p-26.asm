     bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare extern functions used by the program
extern exit, printf, scanf         ; add printf as extern function            
import exit msvcrt.dll    
import printf msvcrt.dll    ; tell the assembler that function printf can be found in library msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a resd 1
    b resd 1
    k dw 5
    result dq 0
    msj_a db "a=",0
    msj_b db "b=",0
    format db "(%d + %d) * k = %ld", 0
    read_format db "%d", 0

; our code starts here
segment code use32 class=code
    start:
        push dword msj_a
        call [printf]
        add esp, 4
        
        push dword a
        push dword read_format
        call [scanf]
        add esp, 4*2
        
        push dword msj_b
        call [printf]
        add esp, 4*1
        
        push dword b
        push dword read_format
        call [scanf]
        add esp, 4*2
        
        mov eax ,[a]
        sub eax, dword [b]
        mul dword [k]
        
        mov dword [result] ,eax
        mov dword [result + 4], edx
        push dword [result + 4]
        push dword [result]
        push dword [b]
        push dword [a]
        push dword format
        call [printf]
        add esp, 4*5
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
