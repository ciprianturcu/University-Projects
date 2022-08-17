bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll 
import scanf msvcrt.dll   ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    len_sir dd 0
    nr_citit dd 0
    read_format db "%d",0
    sir_bytes resb 100
    sir_dword resd 100
    suma db 0

; our code starts here
segment code use32 class=code
    start:
        push dword len_sir
        push dword read_format
        call [scanf]
        add esp, 4*2
        mov ecx, [len_sir]
        mov edi, sir_dword
        .loop:
            pusha
            push dword nr_citit
            push dword read_format
            call [scanf]
            add esp,4*2
            popa
            lodsd
        loop .loop
        
        mov ecx, [len_sir]
        mov esi,sir_dword
        mov edi, sir_bytes
        .loop2:
            lodsd
            mov ebx,0
            mov dword [suma],ebx
            mov bx,10
            cmp ax,0
            je .gata
                .loop_nr:
                div bx
                cmp dx,1
                jnz .impar
                add byte [suma],dl
                .impar:
                cmp ax,0
                jne .loop_nr
            .gata:
            mov eax,[suma]
            stosb
        loop .loop2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
