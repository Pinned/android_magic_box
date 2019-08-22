
package com.knero.android.tools.file.browser.utils;


import com.knero.android.tools.file.browser.R;

public class FileThumbnailUtil {

    private static int[] fileicon_l = {
            R.drawable.ft_ac3_l, R.drawable.ft_ai_l, R.drawable.ft_aiff_l, R.drawable.ft_ani_l, R.drawable.ft_asf_l,
            R.drawable.ft_au_l, R.drawable.ft_avi_l, R.drawable.ft_bat_l, R.drawable.ft_bin_l, R.drawable.ft_bmp_l,
            R.drawable.ft_bup_l, R.drawable.ft_cab_l, R.drawable.ft_cal_l, R.drawable.ft_cat_l, R.drawable.ft_cur_l,
            R.drawable.ft_dat_l, R.drawable.ft_dcr_l, R.drawable.ft_der_l, R.drawable.ft_dic_l, R.drawable.ft_dll_l,
            R.drawable.ft_doc_l, R.drawable.ft_docx_l, R.drawable.ft_dvd_l, R.drawable.ft_dwg_l, R.drawable.ft_dwt_l,
            R.drawable.ft_fon_l, R.drawable.ft_gif_l, R.drawable.ft_hlp_l, R.drawable.ft_hst_l, R.drawable.ft_html_l,
            R.drawable.ft_ico_l, R.drawable.ft_ifo_l, R.drawable.ft_inf_l, R.drawable.ft_ini_l, R.drawable.ft_java_l,
            R.drawable.ft_jif_l, R.drawable.ft_jpg_l, R.drawable.ft_log_l, R.drawable.ft_m4a_l, R.drawable.ft_mmm_l,
            R.drawable.ft_mov_l, R.drawable.ft_mp2_l, R.drawable.ft_mp2v_l, R.drawable.ft_mp3_l, R.drawable.ft_mp4_l,
            R.drawable.ft_mpeg_l, R.drawable.ft_msp_l, R.drawable.ft_pdf_l, R.drawable.ft_png_l, R.drawable.ft_ppt_l,
            R.drawable.ft_pptx_l, R.drawable.ft_psd_l, R.drawable.ft_ra_l, R.drawable.ft_rar_l, R.drawable.ft_reg_l,
            R.drawable.ft_rtf_l, R.drawable.ft_theme_l, R.drawable.ft_tiff_l, R.drawable.ft_tlb_l, R.drawable.ft_ttf_l,
            R.drawable.ft_txt_l, R.drawable.ft_vob_l, R.drawable.ft_wav_l, R.drawable.ft_wma_l, R.drawable.ft_wmv_l,
            R.drawable.ft_wpl_l, R.drawable.ft_wri_l, R.drawable.ft_xls_l, R.drawable.ft_xlsx_l, R.drawable.ft_xml_l,
            R.drawable.ft_xsl_l, R.drawable.ft_zip_l, R.drawable.ft_apk_l, R.drawable.ft_rmvb_l, R.drawable.ft_mkv_l,
            R.drawable.ft_epub_l, R.drawable.ft_flv_l, R.drawable.ft_gp_l, R.drawable.ft_mpg_l, R.drawable.ft_rm_l,
            R.drawable.ft_ape_l, R.drawable.ft_flac_l, R.drawable.ft_amr_l, R.drawable.ft_ram_l, R.drawable.ft_mod_l,
            R.drawable.ft_mid_l, R.drawable.ft_aif_l, R.drawable.ft_ogg_l, R.drawable.ft_cd_l, R.drawable.ft_aac_l,
            R.drawable.ft_aifc_l, R.drawable.ft_mmf_l, R.drawable.ft_font_l, R.drawable.ft_fla_l, R.drawable.ft_swf_l,
            R.drawable.ft_iso_l, R.drawable.ft_jpg_l
    };

    private static String[] fileExNames = {
            "ac3", "ai", "aiff", "ani", "asf", "au", "avi", "bat", "bin", "bmp", "bup",
            "cab", "cal", "cat", "cur", "dat", "dcr", "der", "dic", "dll", "doc", "docx", "dvd", "dwg", "dwt", "fon",
            "gif", "hlp", "hst", "html", "ico", "ifo", "inf", "ini", "java", "jif", "jpg", "log", "m4a", "mmm", "mov",
            "mp2", "mp2v", "mp3", "mp4", "mpeg", "msp", "pdf", "png", "ppt", "pptx", "psd", "ra", "rar", "reg", "rtf",
            "theme", "tiff", "tlb", "ttf", "txt", "vob", "wav", "wma", "wmv", "wpl", "wri", "xls", "xlsx", "xml",
            "xsl", "zip", "apk", "rmvb", "mkv", "epub", "flv", "3gp", "mpg", "rm", "ape", "flac", "amr", "ram", "mod",
            "mid", "aif", "ogg", "cd", "aac", "aifc", "mmf", "font", "fla", "swf", "iso", "jpeg"
    };

    public static int getIcon(String fileExName) {
        int len = fileExNames.length;
        if (fileExName != null && !"".equals(fileExName)) {
            for (int i = 0; i < len; i++) {
                if (fileExNames[i].equalsIgnoreCase(fileExName)) {
                    return fileicon_l[i];
                }
            }
        }
        return R.drawable.ic_type_unknown;
    }

    public static String getExtName(String name) {
        if (name != null) {
            int index = name.lastIndexOf(".");
            if (index != -1) {
                return name.substring(index + 1);
            }
        }
        return "";
    }

}
