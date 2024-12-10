<%-- 
    Document   : derivador
    Created on : Jan 14, 2024, 9:46:13 PM
    Author     : tony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="clases.Tree"%>
<%@page import="clases.Node"%>





<!DOCTYPE html>
<html>
    <head>
        <title>Future Imperfect by HTML5 UP</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="assets/css/main.css" />
        <link rel="stylesheet" href="mathquill/mathquill.css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="mathquill/mathquill.js"></script>
        <script>
            var MQ = MathQuill.getInterface(2); // for backcompat
        </script>

    </head>
    <body class="is-preload">
        <div id="main">

            <!-- Post -->
            <article class="post">
                <header>
                    <div class="title">
                        <h2><a href="derivador.jsp">Derivador de funciones </a></h2>
                        <p> Escriba una funcion a derivar </p>
                    </div>
                    <div class="meta">
                        <time class="published" datetime="19/Jan/2023">19/Jan/2023</time>
                        <a  class="author"><span class="name">Made by Tony Rusher</span></a>
                    </div>

                </header>   

                <form name="derivadorForm" action="derivador.jsp">
                   
                    <!--<input hidden type="text" id = "funcion" name="funcion" ><br/>--> 
                    <input  type="text" id = "funcion" name="funcion" ><br/> 

                    <p><span id="answer" name ="answer" class = "form-control"></span></p>
                    <input type="submit" class = "form-control" value="Derivar" > 
                    <!--<span id="input" name ="input"></span>-->


                    <script>
                        var funcInput = document.getElementById('answer');
                        var txtFuncion = document.getElementById('funcion');
                        var funcMathField = MQ.MathField(funcInput, {
                            handlers: {
                                edit: function () {
                                    txtFuncion.value = funcMathField.latex();
                                }
                            }
                        });



                    </script>

                </form>

                <%
                    String exp = request.getParameter("funcion");
                   
                    if(exp != null && !exp.equals("")){
                        exp = exp.replace("\\", "\\\\");

                        String ans = "";
                        try{
                            Tree tr = new Tree(exp);
                            ans = tr.derivate();
                        }catch(Exception e){
                            ans = "\\textup{Expresion erronea, ingrese de nuevo.}";
                        }
                       
                %>
                <script>
                    var lst = document.getElementById('funcion');
                    lst.value = "<%=exp%>";
                    funcMathField.write("<%=exp%>");
                </script>
                <%
                %>
                <article>
                    <header>
                        <h2 >La derivada es: </h2>


                        <span id="fill-in-the-blank"><%=ans%></span>
                        <script>
                            var fillInTheBlank = MQ.StaticMath(document.getElementById('fill-in-the-blank'));

                        </script>
                    </header>
                </article>
                <%
                    }
                %>





            </article>



        </div>
    </body>
</html> 