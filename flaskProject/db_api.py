import pymysql


class DatabaseOP:
    def __init__(self):
        # Connect to the database
        self.conn = pymysql.connect(
            host='localhost',
            user='root',
            password='mysql123',
            database='tysql',
            charset='utf8mb4'
        )

    def __del__(self):
        self.conn.close()

    def select(self, sentences):
        response = ""
        print("In select...")

        # 创建游标
        cursor = self.conn.cursor()

        # 执行每个查询语句
        for sentence in sentences:
            cursor.execute(sentence)

            # 获取查询结果
            result = cursor.fetchall()
            for row in result:
                print(row)
                response += str(row)

        # 关闭游标和数据库连接
        cursor.close()

        return response

    def get_table_structure(self, table_name):
        result = table_name + ":\n"

        # Create a cursor
        cursor = self.conn.cursor()

        # Execute the query to get the table structure
        query = f"SHOW COLUMNS FROM {table_name}"
        cursor.execute(query)

        # Get the query results
        rows = cursor.fetchall()
        for row in rows:
            # Append each row's content to the result string
            result += str(row) + "\n"

        # Close the cursor
        cursor.close()

        return result

    def get_table_field_constraints(self, *tables):
        result = []

        try:
            cursor = self.conn.cursor()

            for table_name in tables:
                # Get the table structure
                query = f"SHOW COLUMNS FROM {table_name}"
                cursor.execute(query)
                rows = cursor.fetchall()

                for row in rows:
                    field_name = row[0]

                    # Get the foreign key constraints
                    query = f"SELECT TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, " \
                            f"REFERENCED_COLUMN_NAME " \
                            f"FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " \
                            f"WHERE TABLE_SCHEMA = 'tysql' " \
                            f"AND REFERENCED_TABLE_NAME = '{table_name}' " \
                            f"AND REFERENCED_COLUMN_NAME = '{field_name}'"
                    cursor.execute(query)
                    constraints = cursor.fetchall()

                    # Extract constraint information
                    constraint_info = []
                    for constraint in constraints:
                        referenced_table = constraint[0]
                        referenced_column = constraint[1]
                        constraint_name = constraint[2]
                        constraint_info.append(f"{referenced_table}.{referenced_column} ({constraint_name})")

                    # Build the result
                    result.append((table_name, field_name, constraint_info))

        except pymysql.Error as e:
            print(f"Error: {e}")

        finally:
            cursor.close()

        return result
