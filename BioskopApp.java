import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/** Aplikasi Pemesanan Tiket Bioskop - Java OOP + Swing. */
public class BioskopApp extends JFrame {
    private static final Color BG = new Color(15, 23, 42);
    private static final Color PANEL = new Color(30, 41, 59);
    private static final Color LIGHT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(148, 163, 184);
    private static final Color ACCENT = new Color(56, 189, 248);
    private static final Color GREEN = new Color(34, 197, 94);
    private static final Color RED = new Color(239, 68, 68);
    private static final Color GOLD = new Color(251, 191, 36);

    private final List<JadwalTayang> jadwal = new ArrayList<>();
    private final Set<String> dipilih = new LinkedHashSet<>();
    private JadwalTayang jadwalAktif;
    private final JComboBox<JadwalTayang> jadwalCombo = new JComboBox<>();
    private final JPanel kursiPanel = new JPanel();
    private final JLabel filmInfo = new JLabel();
    private final JLabel studioInfo = new JLabel();
    private final JLabel kursiInfo = new JLabel();
    private final JLabel totalInfo = new JLabel();
    private final JTextField namaField = new JTextField();
    private final JButton pesanButton = new JButton("PESAN TIKET");

    public BioskopApp() {
        super("CINEMAX - Aplikasi Pemesanan Tiket Bioskop");
        siapkanData();
        bangunTampilan();
        jadwalCombo.setSelectedIndex(0);
        gantiJadwal();
    }

    private void siapkanData() {
        Film f1 = new Film("Petualangan Nusantara", "Petualangan", 125, "SU");
        Film f2 = new Film("Misteri Tengah Malam", "Horor", 105, "17+");
        Film f3 = new Film("Cinta di Ujung Senja", "Romantis", 110, "13+");
        Film f4 = new Film("Galaksi Terakhir", "Sci-Fi", 140, "13+");
        Studio s1 = new Studio("Studio 1", "Regular", 5, 8, 35000);
        Studio s2 = new Studio("Studio 2", "Premium", 5, 8, 50000);
        Studio s3 = new Studio("Studio 3", "Regular", 6, 8, 40000);
        jadwal.add(new JadwalTayang(f1, s1, "10.00"));
        jadwal.add(new JadwalTayang(f1, s2, "15.30"));
        jadwal.add(new JadwalTayang(f2, s3, "19.00"));
        jadwal.add(new JadwalTayang(f3, s1, "13.00"));
        jadwal.add(new JadwalTayang(f4, s2, "20.00"));
        // Contoh kursi yang sudah dipesan pada jadwal tertentu
        jadwal.get(0).pesanKursi(Arrays.asList("A3", "B4", "C5"));
        jadwal.get(2).pesanKursi(Arrays.asList("A1", "A2", "D4"));
    }

    private void bangunTampilan() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 760);
        setMinimumSize(new Dimension(920, 660));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 18));

        JPanel header = panel(BG);
        header.setBorder(new EmptyBorder(22, 28, 0, 28));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel title = label("CINEMAX  |  BOOK YOUR SEAT", 27, Font.BOLD, ACCENT);
        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(label("Pilih film, tentukan kursi, lalu pesan tiketmu!", 13, Font.PLAIN, MUTED));
        add(header, BorderLayout.NORTH);

        JPanel main = panel(BG);
        main.setBorder(new EmptyBorder(0, 28, 24, 28));
        main.setLayout(new BorderLayout(18, 0));
        add(main, BorderLayout.CENTER);

        JPanel left = panel(PANEL);
        left.setBorder(new CompoundBorder(new LineBorder(new Color(51, 65, 85), 1, true), new EmptyBorder(20, 20, 20, 20)));
        left.setLayout(new BorderLayout(0, 14));
        main.add(left, BorderLayout.CENTER);

        JPanel select = panel(PANEL);
        select.setLayout(new BoxLayout(select, BoxLayout.Y_AXIS));
        select.add(label("PILIH FILM & JADWAL", 14, Font.BOLD, LIGHT));
        select.add(Box.createVerticalStrut(9));
        jadwalCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        jadwalCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        jadwalCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (JadwalTayang j : jadwal) jadwalCombo.addItem(j);
        jadwalCombo.addActionListener(e -> gantiJadwal());
        select.add(jadwalCombo);
        select.add(Box.createVerticalStrut(10));
        filmInfo.setForeground(LIGHT);
        filmInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        select.add(filmInfo);
        select.add(Box.createVerticalStrut(5));
        studioInfo.setForeground(MUTED);
        studioInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        select.add(studioInfo);
        left.add(select, BorderLayout.NORTH);

        JPanel seatSection = panel(PANEL);
        seatSection.setLayout(new BorderLayout(0, 14));
        JLabel screen = label("L A Y A R   B I O S K O P", 12, Font.BOLD, BG);
        screen.setHorizontalAlignment(SwingConstants.CENTER);
        screen.setOpaque(true);
        screen.setBackground(new Color(125, 211, 252));
        screen.setBorder(new EmptyBorder(9, 0, 9, 0));
        seatSection.add(screen, BorderLayout.NORTH);
        kursiPanel.setBackground(PANEL);
        seatSection.add(kursiPanel, BorderLayout.CENTER);
        left.add(seatSection, BorderLayout.CENTER);

        JPanel legend = panel(PANEL);
        legend.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        legend.add(legendItem(new Color(71, 85, 105), "Tersedia"));
        legend.add(legendItem(GREEN, "Dipilih"));
        legend.add(legendItem(RED, "Terisi"));
        left.add(legend, BorderLayout.SOUTH);

        JPanel right = panel(PANEL);
        right.setPreferredSize(new Dimension(310, 10));
        right.setBorder(new CompoundBorder(new LineBorder(new Color(51, 65, 85), 1, true), new EmptyBorder(22, 19, 22, 19)));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        main.add(right, BorderLayout.EAST);
        right.add(label("DETAIL PEMESANAN", 18, Font.BOLD, LIGHT));
        right.add(Box.createVerticalStrut(18));
        right.add(label("Nama pemesan", 12, Font.PLAIN, MUTED));
        right.add(Box.createVerticalStrut(7));
        namaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        namaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        right.add(namaField);
        right.add(Box.createVerticalStrut(20));
        right.add(label("Kursi terpilih", 12, Font.PLAIN, MUTED));
        right.add(Box.createVerticalStrut(8));
        kursiInfo.setFont(new Font("SansSerif", Font.BOLD, 16));
        kursiInfo.setForeground(LIGHT);
        right.add(kursiInfo);
        right.add(Box.createVerticalStrut(20));
        right.add(label("Total pembayaran", 12, Font.PLAIN, MUTED));
        right.add(Box.createVerticalStrut(7));
        totalInfo.setFont(new Font("SansSerif", Font.BOLD, 24));
        totalInfo.setForeground(GOLD);
        right.add(totalInfo);
        right.add(Box.createVerticalStrut(22));
        pesanButton.setBackground(ACCENT);
        pesanButton.setForeground(BG);
        pesanButton.setFocusPainted(false);
        pesanButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        pesanButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        pesanButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        pesanButton.addActionListener(e -> pesanTiket());
        right.add(pesanButton);
        right.add(Box.createVerticalStrut(12));
        JButton resetButton = new JButton("HAPUS PILIHAN KURSI");
        resetButton.setForeground(LIGHT);
        resetButton.setBackground(new Color(51, 65, 85));
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> { dipilih.clear(); renderKursi(); updateRingkasan(); });
        right.add(resetButton);
        right.add(Box.createVerticalGlue());
        JLabel note = label("* Kursi merah tidak dapat dipesan.", 11, Font.PLAIN, MUTED);
        right.add(note);
    }

    private void gantiJadwal() {
        jadwalAktif = (JadwalTayang) jadwalCombo.getSelectedItem();
        if (jadwalAktif == null) return;
        dipilih.clear();
        Film f = jadwalAktif.getFilm();
        Studio s = jadwalAktif.getStudio();
        filmInfo.setText(f.getGenre() + "  •  " + f.getDurasiMenit() + " menit  •  Rating " + f.getRating());
        studioInfo.setText(s.getNama() + "  •  " + s.getTipe() + "  •  " + rupiah(s.getHarga()) + "/kursi");
        renderKursi();
        updateRingkasan();
    }

    private void renderKursi() {
        if (jadwalAktif == null) return;
        Studio s = jadwalAktif.getStudio();
        kursiPanel.removeAll();
        kursiPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        for (int r = 0; r < s.getBaris(); r++) {
            for (int k = 1; k <= s.getKolom(); k++) {
                String nomor = "" + (char) ('A' + r) + k;
                boolean terisi = jadwalAktif.isTerisi(nomor);
                JButton btn = new JButton(nomor);
                btn.setPreferredSize(new Dimension(54, 46));
                btn.setFocusPainted(false);
                btn.setFont(new Font("SansSerif", Font.BOLD, 12));
                btn.setForeground(Color.WHITE);
                btn.setBackground(terisi ? RED : dipilih.contains(nomor) ? GREEN : new Color(71, 85, 105));
                btn.setEnabled(!terisi);
                if (!terisi) {
                    btn.addActionListener(e -> {
                        if (!dipilih.add(nomor)) dipilih.remove(nomor);
                        renderKursi();
                        updateRingkasan();
                    });
                }
                c.gridx = k - 1;
                c.gridy = r;
                // Jarak lorong antara kursi ke-4 dan ke-5.
                c.insets = new Insets(5, k == 5 ? 25 : 5, 5, 5);
                kursiPanel.add(btn, c);
            }
        }
        kursiPanel.revalidate();
        kursiPanel.repaint();
    }

    private void updateRingkasan() {
        kursiInfo.setText(dipilih.isEmpty() ? "Belum memilih kursi" : String.join(", ", dipilih));
        long total = jadwalAktif == null ? 0 : (long) dipilih.size() * jadwalAktif.getStudio().getHarga();
        totalInfo.setText(rupiah(total));
        pesanButton.setEnabled(!dipilih.isEmpty());
    }

    private void pesanTiket() {
        String nama = namaField.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi nama pemesan terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
            namaField.requestFocus();
            return;
        }
        if (dipilih.isEmpty()) return;
        Pemesanan pesanan = new Pemesanan(nama, jadwalAktif, new ArrayList<>(dipilih));
        int jawab = JOptionPane.showConfirmDialog(this, pesanan.ringkasan(), "Konfirmasi Pemesanan", JOptionPane.YES_NO_OPTION);
        if (jawab != JOptionPane.YES_OPTION) return;
        try {
            jadwalAktif.pesanKursi(pesanan.getKursi());
            JOptionPane.showMessageDialog(this, pesanan.struk(), "Tiket Berhasil Dipesan", JOptionPane.INFORMATION_MESSAGE);
            dipilih.clear();
            namaField.setText("");
            renderKursi();
            updateRingkasan();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JPanel panel(Color bg) {
        JPanel p = new JPanel();
        p.setBackground(bg);
        return p;
    }

    private static JLabel label(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", style, size));
        l.setForeground(color);
        return l;
    }

    private static JPanel legendItem(Color color, String caption) {
        JPanel item = panel(PANEL);
        item.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel dot = label("■", 18, Font.BOLD, color);
        item.add(dot);
        item.add(label(caption, 12, Font.PLAIN, LIGHT));
        return item;
    }

    private static String rupiah(long nilai) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        return "Rp " + nf.format(nilai);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) { }
            new BioskopApp().setVisible(true);
        });
    }
}

// ENCAPSULATION: atribut private, dibaca melalui getter.
class Film {
    private final String judul, genre, rating;
    private final int durasiMenit;

    public Film(String judul, String genre, int durasiMenit, String rating) {
        this.judul = judul;
        this.genre = genre;
        this.durasiMenit = durasiMenit;
        this.rating = rating;
    }
    public String getJudul() { return judul; }
    public String getGenre() { return genre; }
    public int getDurasiMenit() { return durasiMenit; }
    public String getRating() { return rating; }
}

class Studio {
    private final String nama, tipe;
    private final int baris, kolom, harga;

    public Studio(String nama, String tipe, int baris, int kolom, int harga) {
        if (baris < 1 || baris > 26 || kolom < 1 || harga < 0) throw new IllegalArgumentException("Data studio tidak valid");
        this.nama = nama;
        this.tipe = tipe;
        this.baris = baris;
        this.kolom = kolom;
        this.harga = harga;
    }
    public String getNama() { return nama; }
    public String getTipe() { return tipe; }
    public int getBaris() { return baris; }
    public int getKolom() { return kolom; }
    public int getHarga() { return harga; }
    public boolean kursiValid(String nomor) {
        if (nomor == null || !nomor.matches("[A-Z][1-9][0-9]*")) return false;
        int r = nomor.charAt(0) - 'A';
        int k = Integer.parseInt(nomor.substring(1));
        return r >= 0 && r < baris && k <= kolom;
    }
}

// ABSTRACTION: aturan ketersediaan kursi dikelola oleh JadwalTayang,
// bukan oleh tombol-tombol UI.
class JadwalTayang {
    private final Film film;
    private final Studio studio;
    private final String jam;
    private final Set<String> kursiTerisi = new HashSet<>();

    public JadwalTayang(Film film, Studio studio, String jam) {
        this.film = Objects.requireNonNull(film);
        this.studio = Objects.requireNonNull(studio);
        this.jam = jam;
    }
    public Film getFilm() { return film; }
    public Studio getStudio() { return studio; }
    public String getJam() { return jam; }
    public boolean isTerisi(String nomor) { return kursiTerisi.contains(nomor); }

    public void pesanKursi(List<String> kursi) {
        if (kursi == null || kursi.isEmpty()) throw new IllegalArgumentException("Tidak ada kursi dipilih");
        Set<String> unik = new HashSet<>(kursi);
        if (unik.size() != kursi.size()) throw new IllegalArgumentException("Ada kursi ganda");
        for (String k : kursi) {
            if (!studio.kursiValid(k) || isTerisi(k))
                throw new IllegalArgumentException("Kursi " + k + " tidak tersedia!");
        }
        kursiTerisi.addAll(kursi);
    }

    @Override
    public String toString() {
        return film.getJudul() + " | " + studio.getNama() + " | " + jam;
    }
}

class Pemesanan {
    private static int counter = 1000;
    private final String kode, nama;
    private final JadwalTayang jadwal;
    private final List<String> kursi;

    public Pemesanan(String nama, JadwalTayang jadwal, List<String> kursi) {
        this.kode = "CMX-" + (++counter);
        this.nama = nama;
        this.jadwal = jadwal;
        this.kursi = Collections.unmodifiableList(new ArrayList<>(kursi));
    }
    public List<String> getKursi() { return kursi; }
    public long hitungTotal() { return (long) kursi.size() * jadwal.getStudio().getHarga(); }
    public String ringkasan() {
        return "Pemesan: " + nama + "\nFilm: " + jadwal.getFilm().getJudul()
                + "\nStudio: " + jadwal.getStudio().getNama() + "\nJam: " + jadwal.getJam()
                + "\nKursi: " + String.join(", ", kursi)
                + "\nTotal: " + formatRupiah(hitungTotal()) + "\n\nLanjutkan pemesanan?";
    }
    public String struk() {
        String tanggal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID")));
        return "=== E-TICKET CINEMAX ===\n"
                + "Kode: " + kode + "\nTanggal pemesanan: " + tanggal + "\n"
                + "Nama: " + nama + "\nFilm: " + jadwal.getFilm().getJudul()
                + "\nStudio: " + jadwal.getStudio().getNama() + "\nJam tayang: " + jadwal.getJam()
                + "\nKursi: " + String.join(", ", kursi)
                + "\nTotal: " + formatRupiah(hitungTotal()) + "\n\nTerima kasih!";
    }
    private static String formatRupiah(long nilai) {
        return "Rp " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format(nilai);
    }
}
