import sqlite3
import pandas as pd
import sys

def export_table_to_csv(db_path, table_name, output_csv):
    try:
        conn = sqlite3.connect(db_path)

        df = pd.read_sql_query(f"SELECT * FROM {table_name}", conn)

        df.to_csv(output_csv, index=False)

        print(f"Successfully exported {table_name} to {output_csv}")
        conn.close()
    except Exception as e:
        print(f"Error: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) == 4:
        script_name = sys.argv[0]
        db_path = sys.argv[1]
        table_name = sys.argv[2]
        output_path = sys.argv[3]
        export_table_to_csv(sys.argv[1], sys.argv[2], sys.argv[3])
    else:
        print("Missing arguments. Required: db_path table_name output_path")