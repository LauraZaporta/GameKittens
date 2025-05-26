using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace API.GameKittens.Migrations
{
    /// <inheritdoc />
    public partial class DatabaseRestructure : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Pets_Accessories_AccessoryId",
                table: "Pets");

            migrationBuilder.DropTable(
                name: "PetAccessories");

            migrationBuilder.DropTable(
                name: "Accessories");

            migrationBuilder.DropIndex(
                name: "IX_Pets_AccessoryId",
                table: "Pets");

            migrationBuilder.DropColumn(
                name: "AccessoryId",
                table: "Pets");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "AccessoryId",
                table: "Pets",
                type: "int",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Accessories",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ImageURL = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Price = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Accessories", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "PetAccessories",
                columns: table => new
                {
                    AvailableAccessoriesId = table.Column<int>(type: "int", nullable: false),
                    AvailableInPetsId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PetAccessories", x => new { x.AvailableAccessoriesId, x.AvailableInPetsId });
                    table.ForeignKey(
                        name: "FK_PetAccessories_Accessories_AvailableAccessoriesId",
                        column: x => x.AvailableAccessoriesId,
                        principalTable: "Accessories",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_PetAccessories_Pets_AvailableInPetsId",
                        column: x => x.AvailableInPetsId,
                        principalTable: "Pets",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Pets_AccessoryId",
                table: "Pets",
                column: "AccessoryId");

            migrationBuilder.CreateIndex(
                name: "IX_PetAccessories_AvailableInPetsId",
                table: "PetAccessories",
                column: "AvailableInPetsId");

            migrationBuilder.AddForeignKey(
                name: "FK_Pets_Accessories_AccessoryId",
                table: "Pets",
                column: "AccessoryId",
                principalTable: "Accessories",
                principalColumn: "Id",
                onDelete: ReferentialAction.SetNull);
        }
    }
}
