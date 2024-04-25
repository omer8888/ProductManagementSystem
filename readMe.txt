Hi
this Project Product Management Web application
created using java Spring Boot MySQL Thymeleaf

implementing MVC :
    Model-View-Controller, which is a software architectural pattern used to structure applications.
    MVC separates an application into three interconnected components (Model, View, Controller)

    Model:
     Represents the application's data and business logic.
      It encapsulates the data and behavior of the application and is responsible for managing the state and responding to queries about its state.

    View:
     Represents the presentation layer of the application.
     It's responsible for rendering the user interface based on the data provided by the model.
      Views display information to the user and capture user input.

    Controller:
     Acts as an intermediary between the model and the view. It receives user input from the view, processes it (often by invoking methods on the model),
    and updates the view accordingly. Controllers handle user interactions and orchestrate the flow of data between the model and the view.

"Service Layer" pattern:
    this project implements "Service Layer" pattern.
    In this pattern, the controller interacts with the service layer,
    which in turn interacts with the database (or other data sources).
    controller-> service -> db

Project functionality:
user can view existing products: directly from db presented in nice table format with products info includes photos,
add new product and upload new photo
update existing product info, or partial details
delete new product and redirect back to the main products page.