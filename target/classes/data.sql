Use personal_data_base;

INSERT INTO roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO categories(id, image_url, name, deleted)
VALUES (1, '/images/CategoryTools.jpg', 'TOOLS', false),
       (2, '/images/CategoryClearcoat.jpg', 'CLEAR', false),
       (3, '/images/CategoryMasking.jpg', 'MASKING', false),
       (4, '/images/CategoryBasecoat.jpg', 'BASECOAT', false),
       (5, '/images/CategoryProtection.jpg', 'PROTECTION', false);


INSERT INTO products(id, description, image_url, price, title, category_id, deleted)
values (1, 'The PHASER spray gun was developed with its special design, special materials
            and a special concept for your very special projects. The unusually elegant design
            was developed in cooperation with Porsche Design Studio.',
        '/images/5500.jpeg', 450.99, 'Spray gun SATAjet X 5500 PHASER', 1, false),
       (2, 'With the SATAjet 5000 B, painters can adjust the inlet pressure and the painting
            distance according to the paint used, the climatic conditions and their working
            style – and achieve perfect results in any situation',
        '/images/5000.jpg', 500.99, 'Spray gun SATAjet 5000 B', 1, false),
       (3, 'The SATAjet 1500 B has been developed for the cost effective application
            of basecoats, clear coats and 2K solid topcoats in car refinishing.
            The compact nozzle spectrum and robust construction ensures excellent results quickly and easily.',
        '/images/1500.jpg', 1333.99, 'Spray gun SATAjet 1500 B', 1, false),
       (4, 'The ideal intermediate sander in the compact class.
            Strengths and benefits
            * Stepless speed preselection, constant speed even under load
            * Longlife thanks to EC technology and sanding pad brake with carbide tip
            * Compact design and weighing only 1.2 kg for optimal ergonomic work conditions
            * Optimal safety while working through vibration detection',
        '/images/festoolSandingMachine.jpg', 500.99, 'FESTOOL ORBITAL SANDER ETS', 1, false),
       (5, 'D800 is an easy-to-use, general purpose, high performance two-pack acrylic
            clearcoat designed for repair and repainting.
            Used with a range of hardeners and thinners it
            offer great performance and productivity across
            all temperature ranges. It is designed to be used
            over DELTRON® GRS basecoat. 1L pack.',
        '/images/d800.png', 46.99, 'CLEAR D800', 2, false),
       (6, 'D8135 is a premium Rapid UHS Clearcoat, optimised for use over
            ENVIROBASE® High Performance waterborne basecoat, designed specifically
            for small repairs, 1-3 panels.
            D8135 delivers an excellent gloss finish in a fast process time and
            is also flexible enough to be used over rigid plastic components.',
        '/images/d8135.png', 71.99, 'CLEAR D8135', 2, false),
       (7, 'D8175 requires only 5 minutes at 60˚C or 20 minutes at 40˚C. The launch
            of this new clearcoat means the Bodyshop does not have to choose between
            sustainability & speed, and quality & performance.  Rapid Performance Clearcoat
            ensures energy savings, speed of repair and a superior finish, contributing to
            Bodyshop profitability, with a product that is also easy to use.',
        '/images/CategoryBasecoat.jpg', 85.99, 'CLEAR D8175', 2, false),
       (8, '3M™ 501E performance masking tape is designed for critical paint masking
            applications in the automotive, specialty vehicle, and industrial markets
            where excellent holding and high temperature performance are required.',
        '/images/3m501E.jpg', 2.99, '3M 501E Masking Tape', 3, false),
       (9, '3M Paper Masking Tape 202 is a high strength natural coloured crepe paper
            coated with a rubber resin adhesive. Designed for indoor use only. Tape should
            not be subjected to outdoor exposure or prolonged periods of sunlight as product
            may become very difficult to remove.', '/images/3m202.jpg', 5.99, '3M 202 High Strength Masking Tape', 3,
        false),
       (10, 'A new green-coloured, smooth creped masking tape from 3M to suit a wide variety
            of bodyshop applications.Using a specially formulated cross-linked rubber/resin adhesive,
            Scotch® Tape 3030 has been developed to provide a good balance of properties for the modern
            bodyshop and specifically in use with water-based paint applications. ',
        '/images/CategoryBasecoat.jpg', 33.99, '3M 3030 Premium Auto Refinish Masking Tape', 3, false),
       (11, 'Easy workflow as part of the straight forward RATIO Aqua System process
            Easy mixing formulas that prevent you from getting the mixture wrong
            Easy application using conventional spraying techniques.',
        '/images/glazurit.jpg', 3000.99, 'Basecoat Glasurit 90-A waterborne mix', 4, false),
       (12, 'All waterborne basecoat systems are definitely not created equal.
            Thanks to its unique, next-generation PPG developed technology,
            the Envirobase High Performance paint system delivers ground-breaking waterborne
            performance characteristics. This industry leading formulation is able to combine
            low VOC ‘green’ credentials with the speed, productivity and user-friendliness a
            modern collision repair centre needs to enhance profitability.',
        '/images/ppgEnvirobase.jpg', 4000.99, 'Basecoat Envirobase Mix waterborne', 4, false),
       (13, 'Mixing system for waterborne basecoats used in automotive refinishing.
            Mixing formulas for the whole range of (solid, metallic and pearl-effect)
            colours needed in automotive refinishing are available. At rather high
            temperatures or when refinishing large surfaces, it is possible to replace
            HB002 by HB004 slow, while replacing HYDROMIX adjusting base by HYDROMIX slow at the same time.',
        '/images/rmOnyx.jpg', 3444.99, 'Basecoat RM Onyx HD Extra MIX', 4, false),
       (14, 'The 6000 Series full facepiece respirator is crafted from lightweight, yet durable materials
        and features a soft silicone faceseal that conforms to the wearer''s face, along with a wide lens
        for visibility and eye protection.',
        '/images/mask3M.jpg', 60.99, 'Full Facepiece Reusable Respirator', 5, false),
       (15, 'SPRAYISM Pro Spray Painting Gloves are ideal for keeping your hands clean when painting.
            They have a comfortable fit with a high level of flexibility. Coated with PU, they are anti-static
            and perfect carrying out precision work.',
        '/images/gloves.jpg', 5.99, 'SPRAYISM Pro Spray Painting Gloves', 5, false),
       (16, 'Anti-static treatment on both sides can help reduce static build-up.
            Reinforced gusset between the legs helps provide increased durability while
            bending and squatting.', '/images/paintSuit.jpg', 22.99, 'Disposable Protective Coverall', 5, false);;

INSERT INTO warehouse(id, quantity, product_id)
VALUES (1, 100, 1),
       (2, 100, 2),
       (3, 100, 3),
       (4, 100, 4),
       (5, 100, 5),
       (6, 100, 6),
       (7, 100, 7),
       (8, 100, 8),
       (9, 100, 9),
       (10, 100, 10),
       (11, 100, 11),
       (12, 100, 12),
       (13, 100, 13),
       (14, 100, 14),
       (15, 100, 15),
       (16, 100, 16);

INSERT INTO users(id, active, address, email, first_name, last_name, password, username, role_id)

VALUES (1, true, 'ADMINADMIN23', 'admin@gmail.com', 'Admin', 'Adminov',
        '25716ae4e2ae20a33f3cc5e42ca30d5862034fa853f3eefa3affc71afd5e781dadd2072b56f04206', 'Admin', 1),
       (2, true, 'Na Pesho Adresa', 'peshkata@gmail.com', 'Pesho', 'Peshev',
        '49f84e0413a83011562b88f71949e3ae5aafdd831e1b5e8246cbe16839dae7dcb5d71ce5c3769060', 'Peshko', 2);
INSERT INTO used_products(id, description, image_url, phone_number, price, title, category_id, user_id)

values (1, 'Lightweight and ergonomically styled for comfort, Balanced air valve design for lighter
            trigger pull and improved air flow',
        '/images/devilbiss-forsale.jpg', '+3598842341235', 200.99, 'Spraygun Devilbiss 1.3', 1, 2),
       (2, 'STAHLWERK compressed air whisper compressor ST 1510 Pro - 150 L boiler.',
        '/images/compressor-forsale.jpg', '+3598842341235', 699.99, 'Compressor', 1, 2);

INSERT INTO orders(id, created_at, deleted, user_id)
VALUES (1, now(), false, 1);

INSERT INTO ordered_products(id, quantity, order_id, product_id)
VALUES (1,1,1,1);

INSERT INTO comments (id, content, created_at, author_id, product_id)
VALUES (1, 'ADMINCOMMENT', now(), 1, 1);
